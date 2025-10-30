package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Cart;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Order;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.OrderItem;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.OrderStatus;
import org.rishabh.eventmanagementsystemadvanced.Mapper.OrderMapper;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.OrderResponse;
import org.rishabh.eventmanagementsystemadvanced.Repository.CartRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.OrderItemRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.OrderRepository;
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
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse placeOrder(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("cart not found"));

        if(cart.getItems().isEmpty()) {
            throw new RuntimeException("cart items not found");
        }
        //cart to order
        Order order = Order.builder()
                .user(cart.getUser())
                .totalAmount(cart.getTotalPrice())
                .status(OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();
        List<OrderItem> orderItems = cart.getItems().stream()
                .map(item -> OrderItem.builder()
                        .order(order)
                        .event(item.getEvent())
                        .ticketType(item.getTicketType())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .collect(Collectors.toList());
        order.setOrderItems(orderItems);
        Order saved = orderRepository.save(order);

        //clear the cart after placing order
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);

        return orderMapper.toResponse(saved);
    }

    @Override
    public OrderResponse viewOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("order not found"));
        return orderMapper.toResponse(order);

    }

    @Override
    public List<OrderResponse> getUserOrders(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("cart not found"));
        if(cart.getItems().isEmpty()) {
            throw new RuntimeException("cart items not found");
        }
        List<Order> orderList = orderRepository.findByUserId(cart.getUser().getId());
        return orderList.stream().map(orderMapper::toResponse).collect(Collectors.toList());

    }


}
