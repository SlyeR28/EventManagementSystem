package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Ticket;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.TicketType;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.TicketValidation;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.User;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.TicketStatus;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.TicketValidationMethod;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.TicketValidationStatus;
import org.rishabh.eventmanagementsystemadvanced.Mapper.TicketMapper;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.TicketResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.TicketRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.TicketRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.TicketTypeRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.TicketValidationRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.UserRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.TicketService;
import org.rishabh.eventmanagementsystemadvanced.Utils.QrGenerator.QrGenerators;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketValidationRepository  ticketValidationRepository;
    private final TicketMapper  ticketMapper;
    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;

    @Override
    public TicketResponse createTicket(TicketRequest request) {
        User purchaser = userRepository.findById(request.getPurchaserId()).orElseThrow(() -> new RuntimeException("User not found"));
        TicketType ticketType = ticketTypeRepository.findById(request.getTicketTypeId()).orElseThrow(() -> new RuntimeException("Ticket Type not found"));

        Ticket ticket = Ticket.builder()
                .ticketType(ticketType)
                .purchaser(purchaser)
                .priceAtPurchase(ticketType.getCurrentPrice())
                .status(TicketStatus.BOOKED)
                .build();
        Ticket saved = ticketRepository.save(ticket);

        // // ✅ Generate QR Code (save locally or later upload to Cloudinary)
        String qrText = "TicketID=" + saved.getId() + "|User=" + purchaser.getFullName();
        String qrPath = "qrcodes/ticket_" + saved.getId() + ".png";
        QrGenerators.generateQrCode(qrText, qrPath);

        saved.setQrCodeUrl(qrPath);

        return ticketMapper.toResponse(saved);
    }

    @Override
    public TicketResponse validateTicket(Long ticketId, TicketValidationMethod method) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        boolean allreadyValidated = ticket.getValidations().stream()
                .anyMatch(v -> v.getValidationStatus() == TicketValidationStatus.VALID);
        TicketValidation validation = TicketValidation.builder()
                .ticket(ticket)
                .validationMethod(method)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        if (allreadyValidated) {
            validation.setValidationStatus(TicketValidationStatus.INVALID);
        }else {
            validation.setValidationStatus(TicketValidationStatus.VALID);
            ticket.setStatus(TicketStatus.BOOKED);
        }
        ticketValidationRepository.save(validation);
        ticket.getValidations().add(validation);
        ticketRepository.save(ticket);

        return ticketMapper.toResponse(ticket);
    }

    @Override
    public TicketResponse getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        return ticketMapper.toResponse(ticket);
    }

    @Override
    public List<TicketResponse> getTicketsByUser(Long userId) {
        List<Ticket> tickets = ticketRepository.findByPurchaserId(userId);
        return tickets.stream()
                .map(ticketMapper::toResponse)
                .collect(Collectors.toList());
    }


}
