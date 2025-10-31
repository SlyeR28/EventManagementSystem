package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Order;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.TicketResponse;

import java.util.List;

public interface TicketService {
    List<TicketResponse> generateTickets(Order order);
    List<TicketResponse>getTicketsByUser(Long userId);
}
