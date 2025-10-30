package org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CartItemResponse {

    private Long cartItemId;
    private Long eventId;
    private String eventName;
    private Long ticketTypeId;
    private String ticketTypeName;
    private double ticketPrice;
    private int quantity;
    private double totalPrice;
}
