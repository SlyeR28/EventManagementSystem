package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Event;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.EventDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.EventRequest;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.SalesTimeRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface  EventService {


    EventDto createEvent(Long organizerId, EventRequest eventRequest);

    EventDto updateEvent(Long organizerId, Long eventId, EventRequest eventRequest);

    EventDto getEvent(Long eventId);

    List<EventDto> getAllEvents();

    void deleteEvent(Long organizerId, Long eventId);

    List<EventDto> getEventsByOrganizerId(Long organizerId);

    List<EventDto> getEventsByCategoryId(Long categoryId);

    EventDto publishEvent(Long organizerId, Long eventId);

    EventDto startSalesTime(Long organizerId, Long eventId, SalesTimeRequest request);

    void autoUpdateEvent(Event event);
}
