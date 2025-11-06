package org.rishabh.eventmanagementsystemadvanced.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ApiResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.EventDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.EventRequest;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.SalesTimeRequest;
import org.rishabh.eventmanagementsystemadvanced.Services.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;



    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @PostMapping("/create/{organizerId}")
    public ResponseEntity<EventDto> createEvent(
            @PathVariable Long organizerId,
            @Valid @RequestBody EventRequest eventRequest) {

        EventDto createdEvent = eventService.createEvent(organizerId, eventRequest);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @PutMapping("/update/{organizerId}/{eventId}")
    public ResponseEntity<EventDto> updateEvent(
            @PathVariable Long organizerId,
            @PathVariable Long eventId,
            @Valid @RequestBody EventRequest eventRequest) {

        EventDto updatedEvent = eventService.updateEvent(organizerId, eventId, eventRequest);
        return ResponseEntity.ok(updatedEvent);
    }

    @GetMapping("/get/{eventId}")
    public ResponseEntity<EventDto> getEvent(@PathVariable Long eventId) {
        EventDto event = eventService.getEvent(eventId);
        return ResponseEntity.ok(event);
    }


    @GetMapping("/")
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<EventDto> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @DeleteMapping("/delete/{organizerId}/{eventId}")
    public ResponseEntity<ApiResponse> deleteEvent(
            @PathVariable Long organizerId,
            @PathVariable Long eventId) {

        eventService.deleteEvent(organizerId, eventId);
        return ResponseEntity.ok(new ApiResponse("Event deleted successfully"));
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<List<EventDto>> getEventsByOrganizer(@PathVariable Long organizerId) {
        List<EventDto> events = eventService.getEventsByOrganizerId(organizerId);
        return ResponseEntity.ok(events);
    }


    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<EventDto>> getEventsByCategory(@PathVariable Long categoryId) {
        List<EventDto> events = eventService.getEventsByCategoryId(categoryId);
        return ResponseEntity.ok(events);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @PatchMapping("/publish/{organizerId}/{eventId}")
    public ResponseEntity<EventDto> publishEvent(
            @PathVariable Long organizerId,
            @PathVariable Long eventId) {

        EventDto eventDto = eventService.publishEvent(organizerId, eventId);
        return ResponseEntity.ok(eventDto);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @PutMapping("/{organizerId}/{eventId}/sales-time")
    public ResponseEntity<EventDto> updateSalesTime(
            @PathVariable Long organizerId,
            @PathVariable Long eventId,
            @Valid @RequestBody SalesTimeRequest request) {

        EventDto updated = eventService.startSalesTime(organizerId, eventId, request);
        return ResponseEntity.ok(updated);
    }
}
