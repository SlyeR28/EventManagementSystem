package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Category;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Event;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.User;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.EventStatus;
import org.rishabh.eventmanagementsystemadvanced.Mapper.EventMapper;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.EventDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.EventRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.CategoryRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.EventRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.UserRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.EventService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventServiceImpl  implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;


    @Override
    public EventDto crateEvent(EventRequest eventRequest) {

        //Fetch Organizer
        User organizer = userRepository.findById(eventRequest.getOrganizerId()).
                orElseThrow(() -> new RuntimeException("User not found with id " + eventRequest.getOrganizerId()));
        // fetch Category
        Category category = categoryRepository.findById(eventRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id " + eventRequest.getCategoryId()));

        //convert eventRequest to Entity
        Event event = eventMapper.toEntity(eventRequest);
        //Assign organizer and category
         event.setOrganizer(organizer);
         event.setCategory(category);
         // set default event status = draft
         event.setStatus(EventStatus.DRAFT);

        Event saved = eventRepository.save(event);

        return eventMapper.toDto(saved);
    }

    @Override
    public EventDto updateEvent(Long eventId, EventRequest eventRequest) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with id " + eventId));
        event.setName(eventRequest.getName());
        event.setVenue(eventRequest.getVenue());
        event.setDescription(eventRequest.getDescription());
        event.setStartTime(eventRequest.getStartTime());
        event.setEndTime(eventRequest.getEndTime());
        event.setSalesStartTime(eventRequest.getSalesStartTime());
        event.setSalesEndTime(eventRequest.getSalesEndTime());

        Event updated = eventRepository.save(event);
        return eventMapper.toDto(updated);

    }

    @Override
    public EventDto getEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with id " + eventId));
            return eventMapper.toDto(event);
    }

    @Override
    public List<EventDto> getAllEvents() {
        List<Event> eventList = eventRepository.findAll();
        return eventList.stream().map(eventMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with id " + eventId));
          eventRepository.delete(event);
    }

    @Override
    public List<EventDto> getEventsByOrganizerId(Long organizerId) {
        User organizer = userRepository.findById(organizerId).
                orElseThrow(() -> new RuntimeException("User not found with id " + organizerId));
        List<Event> byOrganizerId = eventRepository.findByOrganizer_Id(organizer.getId());
        return byOrganizerId.stream().map(eventMapper::toDto).collect(Collectors.toList());

    }

    @Override
    public List<EventDto> getEventsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + categoryId));
        List<Event> byCategoryId = eventRepository.findByCategory_Id(category.getId());
        return byCategoryId.stream().map(eventMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public EventDto publishEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).
                orElseThrow(() -> new RuntimeException("Event not found with id " + eventId));
        event.setStatus(EventStatus.PUBLISHED);
        Event saved = eventRepository.save(event);
        return eventMapper.toDto(saved);
    }

    @Override
    public EventDto updateEventStatus(Long eventId , EventStatus eventStatus) {
        Event event = eventRepository.findById(eventId).
                orElseThrow(() -> new RuntimeException("Event not found with id " + eventId));
        if(event.getStatus() == EventStatus.COMPLETED ||  event.getStatus() == EventStatus.CANCELLED) {
            throw  new IllegalArgumentException("Cannot change status of completed or cancelled event");
        }
        event.setStatus(eventStatus);
        Event updated = eventRepository.save(event);
        return eventMapper.toDto(updated);
    }

    @Override
    public EventDto rescheduleEvent(Long eventId, LocalDateTime newStart, LocalDateTime newEnd) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found: " + eventId));
        event.setStartTime(newStart);
        event.setEndTime(newEnd);
        event.setStatus(EventStatus.RESCHEDULED);
        return eventMapper.toDto(eventRepository.save(event));
    }




    @Override
    public void autoUpdateEvent(Event event) {
        LocalDateTime now = LocalDateTime.now();
        if(event.getStatus() == EventStatus.PUBLISHED) {
            if(now.isAfter(event.getStartTime()) &&  now.isBefore(event.getEndTime())) {
                event.setStatus(EventStatus.ONGOING);
            }else if(now.isAfter(event.getEndTime())) {
                event.setStatus(EventStatus.COMPLETED);
            }
            eventRepository.save(event);
        }
    }

}
