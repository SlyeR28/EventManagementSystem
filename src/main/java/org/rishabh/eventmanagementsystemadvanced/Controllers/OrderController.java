package org.rishabh.eventmanagementsystemadvanced.Controllers;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.OrderResponse;
import org.rishabh.eventmanagementsystemadvanced.Services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService  orderService;

    @PostMapping("/{userId}/checkOut")
    public ResponseEntity<OrderResponse> placeOrder(@PathVariable Long userId) {
        OrderResponse response = orderService.placeOrder(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> viewOrder(@PathVariable Long orderId) {
        OrderResponse response = orderService.viewOrder(orderId);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>>getUserOrder(@PathVariable Long userId) {
        List<OrderResponse> response = orderService.getUserOrders(userId);
        return ResponseEntity.ok(response);
    }

}
