package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Cart;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Order;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.OrderItem;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.OrderStatus;
import org.rishabh.eventmanagementsystemadvanced.Exception.CartNotFoundException;
import org.rishabh.eventmanagementsystemadvanced.Mapper.OrderMapper;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.OrderResponse;
import org.rishabh.eventmanagementsystemadvanced.Repository.CartRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.OrderRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.CartService;
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
        // 1️⃣ Get cart
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart id not found : " +userId));

        if(cart.getItems().isEmpty()) {
            throw new CartNotFoundException("Cart items not found : " +userId );
        }

        // 2️⃣ Create Order entity
        Order order = Order.builder()
                .user(cart.getUser())
                .totalAmount(cart.getTotalPrice())
                .status(OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        // 3️⃣ Create OrderItem entities and link to order
        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> OrderItem.builder()
                        .order(order)
                        .event(cartItem.getEvent())
                        .ticketType(cartItem.getTicketType())
                        .quantity(cartItem.getQuantity())
                        .price(cartItem.getPrice())
                        .build())
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);

        // 4️⃣ Save order (cascade saves items because of cascade = ALL)
        Order savedOrder = orderRepository.save(order);

        // 5️⃣ Mark cart as checked out
        cart.setCheckedOut(true);
        cartRepository.save(cart);


        // 6️⃣ Map to DTO
        return orderMapper.toResponse(savedOrder);
    }


    @Override
    public OrderResponse viewOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CartNotFoundException("order not found : " +orderId));
        return orderMapper.toResponse(order);

    }

    @Override
    public List<OrderResponse> getUserOrders(Long userId) {
        List<Order> orderList = orderRepository.findByUserId(userId);
        return orderList.stream().map(orderMapper::toResponse).collect(Collectors.toList());

    }
}
