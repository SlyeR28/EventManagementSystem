package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.*;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.TicketStatus;
import org.rishabh.eventmanagementsystemadvanced.Exception.TicketNotFoundException;
import org.rishabh.eventmanagementsystemadvanced.Mapper.TicketMapper;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.TicketResponse;
import org.rishabh.eventmanagementsystemadvanced.Repository.OrderRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.TicketRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.TicketService;
import org.rishabh.eventmanagementsystemadvanced.Utils.QrGenerator.QRCodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final QRCodeService qrCodeService;
    private final TicketMapper ticketMapper;

    @Override
    public List<TicketResponse> generateTickets(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket id not found : " ));
        List<Ticket> tickets = new ArrayList<>();

        for(OrderItem item : order.getOrderItems()) {
            TicketType type = item.getTicketType();
            User user = order.getUser();

            for(int i = 0; i<item.getQuantity(); i++) {
               String ticketCode = UUID.randomUUID().toString();
               String qrUrl = qrCodeService.generateQR(ticketCode);


               Ticket ticket = Ticket.builder()
                       .ticketType(type)
                       .purchaser(user)
                       .status(TicketStatus.BOOKED)
                       .priceAtPurchase(type.getCurrentPrice())
                       .qrCodeUrl(qrUrl)
                       .purchasedAt(LocalDateTime.now())
                       .build();

               tickets.add(ticket);

            }
        }
        ticketRepository.saveAll(tickets);

        return tickets.stream().map(ticketMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public TicketResponse getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket id not found : " +id));
        return ticketMapper.toResponse(ticket);
    }

    @Override
    public List<TicketResponse> getTicketsByUser(Long userId) {
        List<Ticket> ticketList = ticketRepository.findByPurchaserId(userId);
        return ticketList.stream().map(ticketMapper::toResponse).collect(Collectors.toList());
    }
}
