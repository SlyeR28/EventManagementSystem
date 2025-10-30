package org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CartResponse {

    private Long cartId;
    private Long userId;
    private String userName;
    private double totalPrice;
    private List<CartItemResponse> items;
    private double cartTotal;
}
