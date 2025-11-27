package org.rishabh.eventmanagementsystemadvanced.PayLoad.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TicketRequest {

    @NotNull(message = "Ticket Type ID is required")
    @Positive(message = "Ticket Type ID must be a positive number")
    private Long ticketTypeId;

    @NotBlank(message = "Ticket Type Name cannot be empty")
    private String ticketTypeName;

    @NotNull(message = "Purchaser ID is required")
    @Positive(message = "Purchaser ID must be positive number")
    private Long purchaserId;
}
