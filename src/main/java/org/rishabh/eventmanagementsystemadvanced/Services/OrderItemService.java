package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.OrderItem;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.OrderItemResponse;

import java.util.List;

public interface OrderItemService {


    // ✅ Updated to return DTOs (for frontend responses)
    List<OrderItemResponse> getItemsByEventId(Long eventId);

    List<OrderItemResponse> getItemsByOrderId(Long orderId);

    // ✅ These remain for internal persistence
    void saveAll(List<OrderItem> orderItemList);

    void deleteByOrderId(Long orderId);


}
