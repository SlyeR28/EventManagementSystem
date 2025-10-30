package org.rishabh.eventmanagementsystemadvanced.PayLoad.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AddToCartRequest  {

    @NotNull(message = "Event must be required")
    private Long eventId;

    @NotNull(message = "TicketTypeId cannot be null")
    private Long ticketTypeId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}
