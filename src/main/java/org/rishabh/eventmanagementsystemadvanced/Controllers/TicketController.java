package org.rishabh.eventmanagementsystemadvanced.Controllers;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.TicketResponse;
import org.rishabh.eventmanagementsystemadvanced.Services.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class TicketController {

   private final TicketService ticketService;


    @PostMapping("/generate/{orderId}")
    public ResponseEntity<List<TicketResponse>> createTicket(@PathVariable Long orderId) {
        List<TicketResponse> generateTickets = ticketService.generateTickets(orderId);
        return ResponseEntity.ok(generateTickets);
    }



    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketResponse>> getTicketsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(ticketService.getTicketsByUser(userId));
    }

}
