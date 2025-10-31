package org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto;

import lombok.Builder;
import lombok.Data;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.TicketValidationMethod;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.TicketValidationStatus;

import java.time.LocalDateTime;

@Builder
@Data
public class ValidationResponse {

    private Long id;
    private TicketValidationStatus validationStatus;
    private TicketValidationMethod validationMethod;
    private LocalDateTime createdAt;
}
