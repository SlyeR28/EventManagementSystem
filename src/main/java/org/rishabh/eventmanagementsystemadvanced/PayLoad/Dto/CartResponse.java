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
    private List<CartItemResponse> items;
    private double totalPrice;

}
