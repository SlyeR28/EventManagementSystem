package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.*;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PricingStrategyType;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.CartItemResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.CartResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.AddToCartRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.*;
import org.rishabh.eventmanagementsystemadvanced.Services.CartService;
import org.rishabh.eventmanagementsystemadvanced.Utils.DynamicPricingEngine.DynamicPriceEngine;
import org.rishabh.eventmanagementsystemadvanced.Utils.DynamicPricingEngine.DynamicPricingFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor

public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final EventRepository eventRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final UserRepository userRepository;
    private final DynamicPricingFactory  dynamicPricingFactory;

    @Override
    public CartResponse addToCart(Long userId, AddToCartRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventRepository.findById(request.getEventId()).orElseThrow(() -> new RuntimeException("Event not found"));

        TicketType ticketType = ticketTypeRepository.findById(request.getTicketTypeId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        if(ticketType.getRemainingQuantity()<request.getQuantity()){
            throw new IllegalArgumentException("Not enough tickets available");
        }
        PricingStrategyType strategyType = ticketType.getEvent() != null ? ticketType.getEvent().getPricingStrategyType()
                     :PricingStrategyType.DEFAULT;

        DynamicPriceEngine engine = dynamicPricingFactory.getStrategy(strategyType);
        engine.applyDynamicPricing(ticketType); // current price update
        ticketTypeRepository.save(ticketType);

        Cart cart1 = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setItems(new ArrayList<>());
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
                    .event(event)
                    .ticketType(ticketType)
                    .quantity(request.getQuantity())
                    .price(request.getQuantity()*ticketType.getCurrentPrice())
                    .cart(cart1)
                    .build();
            cart1.getItems().add(newItem);
        }

        //update total
        double total = cart1.getItems().stream()
                .mapToDouble(CartItem::getPrice)
                .sum();
        cart1.setTotalPrice(total);

        Cart saved = cartRepository.save(cart1);
        return mapToCartResponse(saved);
    }

    @Override
    public CartResponse viewCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));

        for(CartItem cartItem : cart.getItems()){
            TicketType type = cartItem.getTicketType();
            PricingStrategyType strategyType = type.getEvent() != null ? type.getEvent().getPricingStrategyType()
                    :PricingStrategyType.DEFAULT;
            DynamicPriceEngine engine = dynamicPricingFactory.getStrategy(strategyType);
            engine.applyDynamicPricing(type); // current price update
            ticketTypeRepository.save(type);
            cartItem.setPrice(type.getCurrentPrice() * cartItem.getQuantity());

        }

        cart.setTotalPrice(cart.getItems().stream()
                .mapToDouble(CartItem::getPrice)
                .sum());
        cartRepository.save(cart);

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
                        .cartItemId(i.getId())
                        .eventId(i.getTicketType().getEvent().getId())
                        .eventName(i.getTicketType().getEvent().getName())
                        .ticketTypeId(i.getTicketType().getId())
                        .ticketTypeName(i.getTicketType().getName())
                        .ticketPrice(i.getTicketType().getCurrentPrice()) // current unit price
                        .quantity(i.getQuantity())
                        .totalPrice(i.getPrice()) // total = quantity * unit price
                        .build()
                )
                .collect(Collectors.toList());

        return CartResponse.builder()
                .cartId(cart.getId())
                .totalPrice(cart.getTotalPrice())
                .items(items)
                .build();
    }
}
