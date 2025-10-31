package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Order;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.OrderItem;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Ticket;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.TicketStatus;
import org.rishabh.eventmanagementsystemadvanced.Mapper.TicketMapper;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.TicketResponse;
import org.rishabh.eventmanagementsystemadvanced.Repository.TicketRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.TicketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper  ticketMapper;

    @Override
    public List<TicketResponse> generateTickets(Order order) {
        List<Ticket> tickets = new ArrayList<>();

        for (OrderItem item : order.getOrderItems()) {
            for (int i = 0; i < item.getQuantity(); i++) {
                Ticket ticket = Ticket.builder()
                        .ticketType(item.getTicketType())
                        .purchaser(order.getUser())
                        .priceAtPurchase(item.getPrice())
                        .status(TicketStatus.BOOKED)
                        .build();
                tickets.add(ticket);
            }
        }

        List<Ticket> savedTickets = ticketRepository.saveAll(tickets);
        return savedTickets.stream()
                .map(ticketMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketResponse> getTicketsByUser(Long userId) {
        List<Ticket> tickets = ticketRepository.findByPurchaserId(userId);
        return tickets.stream()
                .map(ticketMapper::toResponse)
                .collect(Collectors.toList());
    }
}
