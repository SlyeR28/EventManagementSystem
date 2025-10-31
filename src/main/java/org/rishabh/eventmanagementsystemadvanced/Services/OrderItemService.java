package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.OrderItem;

import java.util.List;

public interface OrderItemService {


    void saveAll(List<OrderItem> orderItemList);

    // Get all items of an order
    List<OrderItem> getItemsByOrderId(Long orderId);

    // Get all items sold for a specific event (optional but useful for analytics)
    List<OrderItem> getItemsByEventId(Long eventId);

    void deleteByOrderId(Long orderId);


}
