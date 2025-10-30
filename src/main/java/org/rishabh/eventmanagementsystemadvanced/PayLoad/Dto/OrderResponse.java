package org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse {

    private Long orderId;
    private String userName;
    private double totalAmount;
    private String orderStatus;
    private String paymentStatus;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;
}
