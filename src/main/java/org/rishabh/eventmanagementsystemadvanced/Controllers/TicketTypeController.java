package org.rishabh.eventmanagementsystemadvanced.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ApiResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.TicketTypeDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.TicketTypeRequest;
import org.rishabh.eventmanagementsystemadvanced.Services.TicketTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket-types")
@RequiredArgsConstructor
public class TicketTypeController {

    private final TicketTypeService ticketTypeService;


    @PostMapping("/create")
    public ResponseEntity<TicketTypeDto> createTicketType(@Valid @RequestBody TicketTypeRequest ticketTypeRequest) {
        TicketTypeDto ticketType = ticketTypeService.createTicketType(ticketTypeRequest);
        return new ResponseEntity<>( ticketType,HttpStatus.CREATED);
    }

    @PutMapping("/update/{ticketTypeId}")
    public ResponseEntity<TicketTypeDto> updateTicketType(@PathVariable long ticketTypeId, @Valid @RequestBody TicketTypeRequest ticketTypeRequest) {
        TicketTypeDto ticketType = ticketTypeService.updateTicketType(ticketTypeId, ticketTypeRequest);
        return new ResponseEntity<>(ticketType,HttpStatus.OK);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<TicketTypeDto>>getTicketTypeByEventId(@PathVariable long eventId) {
        List<TicketTypeDto> byEventId = ticketTypeService.getAllTicketTypesByEventId(eventId);
        return ResponseEntity.ok(byEventId);
    }

    @GetMapping("/ticket/{ticketTypeId}")
    public ResponseEntity<TicketTypeDto> getTicketTypeByTicketTypeId(@PathVariable long ticketTypeId) {
        TicketTypeDto byId = ticketTypeService.getTicketTypeById(ticketTypeId);
        return ResponseEntity.ok(byId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TicketTypeDto>> getAllTicketTypes() {
        List<TicketTypeDto> allTicketTypes = ticketTypeService.getAllTicketTypes();
        return ResponseEntity.ok(allTicketTypes);
    }

    @DeleteMapping("/del/{ticketTypeId}")
    public ResponseEntity<ApiResponse> deleteTicketType(@PathVariable long ticketTypeId) {
        ticketTypeService.deleteTicketType(ticketTypeId);
        return new ResponseEntity<>(new ApiResponse("ticket type deleted successfully") , HttpStatus.OK);
    }
}
