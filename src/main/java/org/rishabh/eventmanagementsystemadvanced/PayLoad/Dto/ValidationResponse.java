package org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto;

import lombok.Builder;
import lombok.Data;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.TicketValidationStatus;

@Builder
@Data
public class ValidationResponse {

    private Long ticketId;
    private TicketValidationStatus validationStatus;

}
