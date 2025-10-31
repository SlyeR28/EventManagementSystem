package org.rishabh.eventmanagementsystemadvanced.Controllers;


import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Mapper.OrderItemMapper;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.OrderItemResponse;
import org.rishabh.eventmanagementsystemadvanced.Services.OrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order-items")
public class OrderItemController {


    private final OrderItemService orderItemService;
    private final OrderItemMapper orderItemMapper;


    // 🔹 Get all items of a specific order

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItemResponse>> getItemsByOrderId(@PathVariable Long orderId) {
        List<OrderItemResponse> responses = orderItemService.getItemsByOrderId(orderId)
                .stream()
                .map(orderItemMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    // 🔹 Get all items sold for a specific event

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<OrderItemResponse>> getItemsByEventId(@PathVariable Long eventId) {
        List<OrderItemResponse> responses = orderItemService.getItemsByEventId(eventId)
                .stream()
                .map(orderItemMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    // 🔹 Delete order items if needed (e.g., admin cancels order)

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<String> deleteItemsByOrder(@PathVariable Long orderId) {
        orderItemService.deleteByOrderId(orderId);
        return ResponseEntity.ok("Order items deleted successfully for order ID: " + orderId);
    }
}
