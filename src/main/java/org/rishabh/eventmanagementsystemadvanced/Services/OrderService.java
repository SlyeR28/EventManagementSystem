package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(Long userId );
    OrderResponse viewOrder( Long orderId);

    List<OrderResponse> getUserOrders(Long userId);
}
