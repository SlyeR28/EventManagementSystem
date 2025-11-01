package org.rishabh.eventmanagementsystemadvanced.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.EventStatus;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ApiResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.EventDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.EventRequest;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.SalesTimeRequest;
import org.rishabh.eventmanagementsystemadvanced.Services.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event")

public class EventController {


    private final EventService eventService;

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @PostMapping("/create")
    public ResponseEntity<EventDto>createEvent(@Valid @RequestBody EventRequest eventRequest) {
        EventDto createdEvent = eventService.crateEvent(eventRequest);
        return  new  ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @PutMapping("/update/{eventId}")
    public ResponseEntity<EventDto>updateEvent(@Valid
                                                @PathVariable Long eventId,
                                               @RequestBody EventRequest eventRequest) {
        EventDto updated = eventService.updateEvent(eventId, eventRequest);
        return  new  ResponseEntity<>( updated , HttpStatus.ACCEPTED);
    }

    @GetMapping("/get/{eventId}")
    public ResponseEntity<EventDto>getEvent(@PathVariable Long eventId) {
        EventDto event = eventService.getEvent(eventId);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/")
    public ResponseEntity<List<EventDto>>getAllEvents() {
        List<EventDto> allEvents = eventService.getAllEvents();
        return ResponseEntity.ok(allEvents);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<List<EventDto>>getAllOrganizerEvents(@PathVariable Long organizerId) {
        List<EventDto> eventsByOrganizerId = eventService.getEventsByOrganizerId(organizerId);
        return ResponseEntity.ok(eventsByOrganizerId);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<EventDto>>getAllCategoryEvents(@PathVariable Long categoryId) {
        List<EventDto> eventsByOrganizerId = eventService.getEventsByCategoryId(categoryId);
        return ResponseEntity.ok(eventsByOrganizerId);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @DeleteMapping("/del/{eventId}")
    public ResponseEntity<ApiResponse>deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok(new ApiResponse("Event deleted successfully"));
    }

    /**
     * ✅ Publish event (change from DRAFT → PUBLISHED)
     */

    @PatchMapping("/publish/{eventId}")
    public ResponseEntity<EventDto>publishEvent(@PathVariable Long eventId) {
        EventDto eventDto = eventService.publishEvent(eventId);
        return ResponseEntity.ok(eventDto);
    }

    /**
     * Update event status (generic — single method for all transitions)
     */

    @PatchMapping("/{eventId}/status")
    public ResponseEntity<EventDto> updateStatus(
            @PathVariable Long eventId,
            @RequestParam("status") EventStatus status) {
        EventDto response = eventService.updateEventStatus(eventId, status);
        return ResponseEntity.ok(response);
    }

    /**
       *  Reschedule event
     */

    @PatchMapping("/{eventId}/reschedule")
    public ResponseEntity<EventDto> rescheduleEvent(
            @PathVariable Long eventId,
            @RequestParam("newStart") LocalDateTime newStart,
            @RequestParam("newEnd") LocalDateTime newEnd) {
        EventDto response = eventService.rescheduleEvent(eventId, newStart, newEnd);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/sales-time")
    public ResponseEntity<EventDto> updateSalesTime(
            @PathVariable Long id,
            @RequestBody SalesTimeRequest request) {
        EventDto response = eventService.startTicketSales(id, request.getSalesStartTime(), request.getSalesEndTime());
        return ResponseEntity.ok(response);
    }

}
