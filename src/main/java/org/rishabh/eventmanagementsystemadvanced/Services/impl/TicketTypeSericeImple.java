package org.rishabh.eventmanagementsystemadvanced.Services.impl;



import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Event;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.TicketType;
import org.rishabh.eventmanagementsystemadvanced.Exception.EventNotFoundException;
import org.rishabh.eventmanagementsystemadvanced.Exception.TicketTypeException;
import org.rishabh.eventmanagementsystemadvanced.Mapper.TicketTypeMapper;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.TicketTypeDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.TicketTypeRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.EventRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.TicketTypeRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.TicketTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketTypeSericeImple implements TicketTypeService {

    private final TicketTypeRepository ticketTypeRepository;
    private final EventRepository eventRepository;
    private final TicketTypeMapper ticketTypeMapper;

    @Override
    public TicketTypeDto createTicketType(TicketTypeRequest ticketTypeRequest) {
        Event event = eventRepository.findById(ticketTypeRequest.getEventId()).
                orElseThrow(() -> new EventNotFoundException("Event not found :: " + ticketTypeRequest.getEventId() ));

        TicketType ticketTypeEntity = ticketTypeMapper.toEntity(ticketTypeRequest);
        // initialize price & qty if not set by mapper
        ticketTypeEntity.setCurrentPrice(ticketTypeEntity.getCurrentPrice());
        ticketTypeEntity.setRemainingQuantity(ticketTypeEntity.getTotalQuantity());

        // maintain both sides
        event.addTicketType(ticketTypeEntity);

        TicketType saved = ticketTypeRepository.save(ticketTypeEntity);
        return ticketTypeMapper.toResponse(saved);

    }

    @Override
    public TicketTypeDto updateTicketType(Long id, TicketTypeRequest ticketTypeRequest) {
        TicketType existing = ticketTypeRepository.findById(id).orElseThrow(
                () -> new TicketTypeException("Ticket type not found : "));
        // update only mutable fields
        existing.setName(ticketTypeRequest.getName());
        existing.setBasePrice(ticketTypeRequest.getBasePrice());
        existing.setTotalQuantity(ticketTypeRequest.getTotalQuantity());

        TicketType updated = ticketTypeRepository.save(existing);

        return ticketTypeMapper.toResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketTypeDto> getAllTicketTypesByEventId(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event  not found : " + eventId));
        List<TicketType> byEventId = ticketTypeRepository.findByEventId(event.getId());
        return byEventId.stream().map(ticketTypeMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketTypeDto> getAllTicketTypes() {
        List<TicketType> all = ticketTypeRepository.findAll();
     return  all.stream().map(ticketTypeMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TicketTypeDto getTicketTypeById(Long id) {
        TicketType ticketType = ticketTypeRepository.findById(id).orElseThrow(
                () -> new TicketTypeException("Ticket type not found : " + id));
        return ticketTypeMapper.toResponse(ticketType);
    }

    @Override
    public void deleteTicketType(Long id) {
        TicketType ticketType = ticketTypeRepository.findById(id)
                .orElseThrow(() -> new TicketTypeException("Ticket id not found"));
        Event event = ticketType.getEvent();
        if (event != null) {
            event.removeTicketType(ticketType);
        }
        ticketTypeRepository.delete(ticketType);

    }
}
