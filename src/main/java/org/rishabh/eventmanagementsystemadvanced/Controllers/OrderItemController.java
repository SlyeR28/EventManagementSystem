package org.rishabh.eventmanagementsystemadvanced.Controllers;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.OrderItemResponse;
import org.rishabh.eventmanagementsystemadvanced.Services.OrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;


    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItemResponse>> getItemsByOrderId(@PathVariable Long orderId) {
        List<OrderItemResponse> responses = orderItemService.getItemsByOrderId(orderId);
        return ResponseEntity.ok(responses);
    }


    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<OrderItemResponse>> getItemsByEventId(@PathVariable Long eventId) {
        List<OrderItemResponse> responses = orderItemService.getItemsByEventId(eventId);
        return ResponseEntity.ok(responses);
    }


    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<String> deleteItemsByOrder(@PathVariable Long orderId) {
        orderItemService.deleteByOrderId(orderId);
        return ResponseEntity.ok("Order items deleted successfully for order ID: " + orderId);
    }
}
