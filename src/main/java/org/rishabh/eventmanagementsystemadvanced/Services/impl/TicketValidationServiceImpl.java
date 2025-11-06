package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.QrCode;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Ticket;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.TicketValidation;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.QrCodeStatusEnum;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.TicketValidationMethod;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.TicketValidationStatus;
import org.rishabh.eventmanagementsystemadvanced.Exception.QrCodeNotFoundException;
import org.rishabh.eventmanagementsystemadvanced.Exception.TicketNotFoundException;
import org.rishabh.eventmanagementsystemadvanced.Mapper.TicketValidationMapper;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ValidationResponse;
import org.rishabh.eventmanagementsystemadvanced.Repository.QrCodeRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.TicketRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.TicketValidationRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.TicketValidationService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketValidationServiceImpl implements TicketValidationService {

    private final TicketValidationRepository ticketValidationRepository;
    private final TicketValidationMapper ticketValidationMapper;
    private final TicketRepository ticketRepository;
    private final QrCodeRepository qrCodeRepository;

    @Override
    public ValidationResponse validateTicketByQrCode(Long qrCodeId) {
        QrCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QrCodeStatusEnum.ACTIVE).orElseThrow(
                () -> new QrCodeNotFoundException(
                        String.format("QrCode with id %s not found", qrCodeId)
                ));
        Ticket ticket = qrCode.getTicket();
        TicketValidation ticketValidation = validateTicket(ticket, TicketValidationMethod.QR_SCAN);
        return ticketValidationMapper.toTicketValidationResponseDto(ticketValidation);

    }

    private TicketValidation validateTicket(Ticket  ticket , TicketValidationMethod  ticketValidationMethod) {
        TicketValidation ticketValidation = new TicketValidation();
        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(ticketValidationMethod);

        TicketValidationStatus validationStatus = ticket.getValidations().stream()
                .filter(v -> TicketValidationStatus.VALID.equals(v.getValidationStatus()))
                .findFirst()
                .map(v -> TicketValidationStatus.INVALID)
                .orElse(TicketValidationStatus.VALID);
        ticketValidation.setValidationStatus(validationStatus);
        return ticketValidationRepository.save(ticketValidation);
    }


    @Override
    public ValidationResponse validateTicketManually(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(TicketNotFoundException::new);
        TicketValidation ticketValidation = validateTicket(ticket, TicketValidationMethod.MANUAL);
        return ticketValidationMapper.toTicketValidationResponseDto(ticketValidation);

    }
}
