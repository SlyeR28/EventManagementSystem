package org.rishabh.eventmanagementsystemadvanced.Controllers;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.TicketValidationMethod;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.TicketResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.TicketRequest;
import org.rishabh.eventmanagementsystemadvanced.Services.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class TicketController {

   private final TicketService ticketService;

    // ✅ Create Ticket (after order or payment)
    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(@RequestBody TicketRequest request) {
        return ResponseEntity.ok(ticketService.createTicket(request));
    }

    // ✅ Validate Ticket (QR_CODE, MANUAL, NFC_SCAN)
    @PostMapping("/{id}/validate")
    public ResponseEntity<TicketResponse> validateTicket(
            @PathVariable Long id,
            @RequestParam TicketValidationMethod method
    ) {
        return ResponseEntity.ok(ticketService.validateTicket(id, method));
    }

    // ✅ Fetch Ticket by ID
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    // ✅ Get User's Tickets
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketResponse>> getTicketsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(ticketService.getTicketsByUser(userId));
    }


}
