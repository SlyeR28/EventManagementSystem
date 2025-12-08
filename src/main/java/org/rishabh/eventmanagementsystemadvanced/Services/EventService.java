package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.EventDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.EventRequest;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.SalesTimeRequest;

import java.util.List;

public interface  EventService {


    EventDto createEvent(EventRequest eventRequest);

    EventDto updateEvent(Long eventId, EventRequest eventRequest);

    EventDto getEvent(Long eventId);

    List<EventDto> getAllEvents();

    void deleteEvent(Long eventId);

    List<EventDto> getEventsByOrganizerId();

    List<EventDto> getEventsByCategoryId(Long categoryId);

    EventDto publishEvent( Long eventId);

    EventDto startSalesTime( Long eventId, SalesTimeRequest request);


    List<EventDto> getEventsByCurrentUser();
}
