package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Cart;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Order;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.OrderItem;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.User;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.OrderStatus;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PaymentStatus;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.OrderItemResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.OrderResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.CreateOrderRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.CartRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.OrderItemRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.OrderRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.UserRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;


    @Override
    public OrderResponse createOrder(Long userId, CreateOrderRequest orderRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        Cart cart = cartRepository.findById(orderRequest.getCartId()).orElseThrow(() -> new RuntimeException("user not found"));

        if(cart.getItems().isEmpty()){
            throw new IllegalArgumentException("Cart is Empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);
        order.setPaymentStatus(PaymentStatus.PENDING);

        List<OrderItem> itemList = cart.getItems().stream().map(
                item -> OrderItem.builder()
                        .order(order)
                        .ticketType(item.getTicketType())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build()
        ).collect(Collectors.toList());

        order.setOrderItems(itemList);
        order.setTotalAmount(cart.getTotalPrice());
        orderRepository.save(order);

        //clear cart
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);

        return null;
    }

    //helper method
    private OrderResponse mapToResponse(Order order){
        List<OrderItemResponse> items = order.getOrderItems().stream()
                .map(oi -> OrderItemResponse.builder()
                        .orderItemId(oi.getId())
                        .eventName(oi.getTicketType().getEvent().getName())
                        .ticketTypeName(oi.getTicketType().getName())
                        .quantity(oi.getQuantity())
                        .price(oi.getPrice())
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .orderId(order.getId())
                .userName(order.getUser().getFullName())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getStatus().toString())
                .paymentStatus(order.getPaymentStatus().toString())
                .createdAt(order.getCreatedAt())
                .items(items)
                .build();
    }

}
