package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Event;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.EventStatus;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.EventDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.EventRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    EventDto crateEvent(EventRequest eventRequest);

    EventDto updateEvent( Long eventId ,EventRequest eventRequest);

    EventDto getEvent(Long eventId);

    List<EventDto> getAllEvents();

    void deleteEvent(Long eventId);

    List<EventDto> getEventsByOrganizerId(Long organizerId);

    List<EventDto>getEventsByCategoryId(Long categoryId);

    EventDto publishEvent(Long eventId);

    EventDto updateEventStatus(Long eventId , EventStatus eventStatus);

    EventDto rescheduleEvent(Long eventId, LocalDateTime newStart, LocalDateTime newEnd);

    void autoUpdateEvent(Event event);

    EventDto startTicketSales(Long eventId, LocalDateTime start, LocalDateTime end);

}
