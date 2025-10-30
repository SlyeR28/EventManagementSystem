package org.rishabh.eventmanagementsystemadvanced.PayLoad.Request;

import lombok.Data;

@Data
public class CreateOrderRequest {

    private Long cartId;
    private String paymentMethod;
}
