package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.TicketResponse;

import java.util.List;

public interface TicketService {
    List<TicketResponse> generateTickets(Long orderId);
    TicketResponse getTicketById(Long id);
    List<TicketResponse> getTicketsByUser(Long userId);
}
