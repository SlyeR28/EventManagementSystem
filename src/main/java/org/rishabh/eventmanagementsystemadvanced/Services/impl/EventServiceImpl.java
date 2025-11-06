package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Category;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Event;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.User;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.EventStatus;
import org.rishabh.eventmanagementsystemadvanced.Exception.*;
import org.rishabh.eventmanagementsystemadvanced.Mapper.EventMapper;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.EventDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.EventRequest;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.SalesTimeRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.CategoryRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.EventRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.UserRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.EventService;
import org.rishabh.eventmanagementsystemadvanced.Utils.EventListeners.DomainEventPublisher;
import org.rishabh.eventmanagementsystemadvanced.Utils.EventListeners.EventLifecycle.EventDraftCreatedEvent;
import org.rishabh.eventmanagementsystemadvanced.Utils.EventListeners.EventLifecycle.TicketSalesStartedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;
    private final DomainEventPublisher domainEventPublisher;


    @Override
    public EventDto createEvent(Long organizerId, EventRequest eventRequest) {

        // Fetch Organizer
        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException("Organizer not found with id " + organizerId));

        // Fetch Category
        Category category = categoryRepository.findById(eventRequest.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id " + eventRequest.getCategoryId()));

        // Map request to entity
        Event event = eventMapper.toEntity(eventRequest);
        event.setOrganizer(organizer);
        event.setCategory(category);
        event.setStatus(EventStatus.DRAFT);

        Event saved = eventRepository.save(event);

        // ✅ Publish event for Notification listener
        domainEventPublisher.publish(
                new EventDraftCreatedEvent(this, saved.getId(), saved.getName(), EventStatus.DRAFT)
        );

        return eventMapper.toDto(saved);
    }



    @Override
    public EventDto updateEvent(Long organizerId, Long eventId, EventRequest eventRequest) {

        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id " + eventId));

        // Check ownership
        if (!existingEvent.getOrganizer().getId().equals(organizerId)) {
            throw new EventUpdateException("You are not authorized to update this event");
        }

        existingEvent.setName(eventRequest.getName());
        existingEvent.setVenue(eventRequest.getVenue());
        existingEvent.setDescription(eventRequest.getDescription());
        existingEvent.setStartTime(eventRequest.getStartTime());
        existingEvent.setEndTime(eventRequest.getEndTime());
        existingEvent.setSalesStartTime(eventRequest.getSalesStartTime());
        existingEvent.setSalesEndTime(eventRequest.getSalesEndTime());

        Event updated = eventRepository.save(existingEvent);
        return eventMapper.toDto(updated);
    }

    @Transactional(readOnly = true)
    @Override
    public EventDto getEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id " + eventId));
        return eventMapper.toDto(event);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EventDto> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEvent(Long organizerId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id " + eventId));

        // Verify ownership before delete
        if (!event.getOrganizer().getId().equals(organizerId)) {
            throw new EventUpdateException("You are not authorized to delete this event");
        }

        eventRepository.delete(event);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EventDto> getEventsByOrganizerId(Long organizerId) {
        return eventRepository.findByOrganizer_Id(organizerId)
                .stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<EventDto> getEventsByCategoryId(Long categoryId) {
        return eventRepository.findByCategory_Id(categoryId)
                .stream()
                .map(eventMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventDto publishEvent(Long organizerId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id " + eventId));

        if (!event.getOrganizer().getId().equals(organizerId)) {
            throw new EventUpdateException("You are not authorized to publish this event");
        }

        event.setStatus(EventStatus.PUBLISHED);
        Event saved = eventRepository.save(event);
        //Notification for event has been published
        domainEventPublisher.publish(
                new EventDraftCreatedEvent(this, saved.getId(), saved.getName(), EventStatus.PUBLISHED)
        );
        return eventMapper.toDto(saved);
    }

    @Override
    public EventDto startSalesTime(Long organizerId, Long eventId, SalesTimeRequest request) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id " + eventId));

        if (!event.getOrganizer().getId().equals(organizerId)) {
            throw new EventUpdateException("You are not authorized to reschedule this event");
        }

        event.setStartTime(request.getSalesStartTime());
        event.setEndTime(request.getSalesEndTime());
        event.setStatus(EventStatus.PUBLISHED);

        // notification for ticket sales are live
        domainEventPublisher.publish(
                new TicketSalesStartedEvent(this ,eventId , event.getName())
        );

        return eventMapper.toDto(eventRepository.save(event));
    }

    @Override
    public void autoUpdateEvent(Event event) {
        LocalDateTime now = LocalDateTime.now();
        if (event.getStatus() == EventStatus.PUBLISHED) {
            if (now.isAfter(event.getStartTime()) && now.isBefore(event.getEndTime())) {
                event.setStatus(EventStatus.ONGOING);
            } else if (now.isAfter(event.getEndTime())) {
                event.setStatus(EventStatus.COMPLETED);
            }
            eventRepository.save(event);
        }
    }
}
