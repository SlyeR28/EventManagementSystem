package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Order;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.TicketValidationMethod;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.TicketResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.TicketRequest;

import java.util.List;

public interface TicketService {
    TicketResponse createTicket(TicketRequest request);
    TicketResponse validateTicket(Long ticketId, TicketValidationMethod method);
    TicketResponse getTicketById(Long id);
    List<TicketResponse> getTicketsByUser(Long userId);
}
