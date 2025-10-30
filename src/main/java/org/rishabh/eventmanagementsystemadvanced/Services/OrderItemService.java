package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.OrderItemResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.OrderItemRequest;

import java.util.List;

public interface OrderItemService {

    // Add an item to an existing order
    OrderItemResponse addItem(Long orderId, OrderItemRequest request);

    // Get all items of an order
    List<OrderItemResponse> getItemsByOrderId(Long orderId);

    // Remove a single item
    void removeItem(Long orderItemId);

    // Update quantity or details of an order item
    OrderItemResponse updateItem(Long orderItemId, OrderItemRequest request);
}
