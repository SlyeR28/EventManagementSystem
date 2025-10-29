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
    private Long ticketTypeId;
    private String eventName;
    private int quantity;
    private double price;
}
