package org.rishabh.eventmanagementsystemadvanced.PayLoad.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.TicketValidationMethod;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationRequest {
    private Long id;
    private TicketValidationMethod ticketValidationMethod;
}
