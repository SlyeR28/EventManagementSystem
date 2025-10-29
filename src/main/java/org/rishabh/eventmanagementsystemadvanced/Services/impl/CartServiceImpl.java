package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.*;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.CartItemResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.CartResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.AddToCartRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.*;
import org.rishabh.eventmanagementsystemadvanced.Services.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor

public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final UserRepository userRepository;

    @Override
    public CartResponse addToCart(Long userId, AddToCartRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        TicketType ticketType = ticketTypeRepository.findById(request.getTicketTypeId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        if(ticketType.getRemainingQuantity()<request.getQuantity()){
            throw new IllegalArgumentException("Not enough tickets available");
        }

        Cart cart1 = cartRepository.findById(userId).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setItems(List.of());
            cart.setTotalPrice(0.0);
            return cartRepository.save(cart);
        });

        CartItem existingItem = cart1.getItems().stream()
                .filter(i -> i.getTicketType().getId().equals(ticketType.getId()))
                .findFirst()
                .orElse(null);
        if(existingItem != null){
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            existingItem.setPrice(existingItem.getQuantity()*ticketType.getCurrentPrice());
        }else{
            CartItem newItem = CartItem.builder()
                    .ticketType(ticketType)
                    .quantity(request.getQuantity())
                    .price(request.getQuantity()*ticketType.getCurrentPrice())
                    .cart(cart1)
                    .build();
            cart1.getItems().add(newItem);
        }
        cartRepository.save(cart1);
        return mapToCartResponse(cart1);
    }

    @Override
    public CartResponse viewCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return mapToCartResponse(cart);
    }

    @Override
    public CartResponse removeItem(Long userId, Long itemId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));
        CartItem cartItem = cartItemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));
        cart.getItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        double total = cart.getItems().stream().mapToDouble(CartItem::getPrice).sum();
        cart.setTotalPrice(total);
        cartRepository.save(cart);

        return mapToCartResponse(cart);
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);

    }

    // helper method
    private CartResponse mapToCartResponse(Cart cart) {
        List<CartItemResponse> items = cart.getItems().stream()
                .map(i -> CartItemResponse.builder()
                        .ticketTypeId(i.getTicketType().getId())
                        .eventName(i.getTicketType().getEvent().getName())
                        .quantity(i.getQuantity())
                        .price(i.getPrice())
                        .build())
                .collect(Collectors.toList());

        return CartResponse.builder()
                .cartId(cart.getId())
                .totalPrice(cart.getTotalPrice())
                .items(items)
                .build();
    }
}
