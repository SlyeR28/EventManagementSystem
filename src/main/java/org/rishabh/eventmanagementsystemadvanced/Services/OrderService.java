package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.OrderResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.CreateOrderRequest;

public interface OrderService {
    OrderResponse createOrder(Long userId , CreateOrderRequest orderRequest);
}
