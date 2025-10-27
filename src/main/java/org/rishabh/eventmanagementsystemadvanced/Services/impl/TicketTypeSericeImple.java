package org.rishabh.eventmanagementsystemadvanced.Services.impl;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Event;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.TicketType;
import org.rishabh.eventmanagementsystemadvanced.Mapper.TicketTypeMapper;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.TicketTypeDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.TicketTypeRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.EventRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.TicketTypeRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.TicketTypeService;
import org.springframework.stereotype.Service;

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
                orElseThrow(() -> new RuntimeException("Event id not found"));

        TicketType ticketTypeEntity = ticketTypeMapper.toEntity(ticketTypeRequest);
        ticketTypeEntity.setEvent(event);
        TicketType saved = ticketTypeRepository.save(ticketTypeEntity);
        return ticketTypeMapper.toResponse(saved);

    }

    @Override
    public TicketTypeDto updateTicketType(Long id, TicketTypeRequest ticketTypeRequest) {
        TicketType ticketType = ticketTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket id not found"));
        TicketType entity = ticketTypeMapper.toEntity(ticketTypeRequest);
        TicketType saved = ticketTypeRepository.save(entity);
        saved.setEvent(ticketType.getEvent());
        return ticketTypeMapper.toResponse(saved);
    }

    @Override
    public List<TicketTypeDto> getAllTicketTypesByEventId(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event id not found"));
        List<TicketType> byEventId = ticketTypeRepository.findByEventId(event.getId());
        return byEventId.stream().map(ticketTypeMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<TicketTypeDto> getAllTicketTypes() {
        List<TicketType> all = ticketTypeRepository.findAll();
     return  all.stream().map(ticketTypeMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public TicketTypeDto getTicketTypeById(Long id) {
        TicketType ticketType = ticketTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket id not found"));
        return ticketTypeMapper.toResponse(ticketType);
    }

    @Override
    public void deleteTicketType(Long id) {
        TicketType ticketType = ticketTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket id not found"));
        ticketTypeRepository.delete(ticketType);

    }
}
