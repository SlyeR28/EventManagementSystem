package org.rishabh.eventmanagementsystemadvanced.PayLoad.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.TicketValidationMethod;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationRequest {

    @NotNull(message = "Ticket ID is Required")
    @Positive(message = "Ticket ID must be a positive number")
    private Long id;

    @NotNull(message = "Ticket validation method cannot be null")
    private TicketValidationMethod ticketValidationMethod;
}
