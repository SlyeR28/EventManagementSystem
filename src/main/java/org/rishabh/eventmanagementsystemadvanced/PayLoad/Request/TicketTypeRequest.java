package org.rishabh.eventmanagementsystemadvanced.PayLoad.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketTypeRequest {

    @NotBlank(message = "Ticket name is required")
    private String name;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Double basePrice;

    @NotNull(message = "Total Quantity tickets are required")
    @Min(value = 1, message = "Total Quantity must be at least 1")
    private Integer totalQuantity;

    @NotNull(message = "Event id is required")
    private Long eventId;

}
