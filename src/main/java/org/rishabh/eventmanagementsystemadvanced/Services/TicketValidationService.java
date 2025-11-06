package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ValidationResponse;

public interface TicketValidationService {
    ValidationResponse validateTicketByQrCode(Long qrCodeId);
    ValidationResponse validateTicketManually(Long ticketId);

}
