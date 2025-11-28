package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.*;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PricingStrategyType;
import org.rishabh.eventmanagementsystemadvanced.Exception.*;
import org.rishabh.eventmanagementsystemadvanced.Mapper.CartMapper;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.CartResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.AddToCartRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.*;
import org.rishabh.eventmanagementsystemadvanced.Services.CartItemService;
import org.rishabh.eventmanagementsystemadvanced.Utils.DynamicPricingEngine.DynamicPriceEngine;
import org.rishabh.eventmanagementsystemadvanced.Utils.DynamicPricingEngine.DynamicPricingFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemServiceImpl implements CartItemService {

        private final CartItemRepository cartItemRepository;
        private final CartRepository cartRepository;
        private final EventRepository eventRepository;
        private final TicketTypeRepository ticketTypeRepository;
        private final UserRepository userRepository;
        private final DynamicPricingFactory dynamicPricingFactory;
        private final CartMapper cartMapper;

        @Override
        @CacheEvict(value = "userCarts", key = "#userId")
        public CartResponse addItemToCart(Long userId, AddToCartRequest request) {
                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new UserNotFoundException("User not found : " + userId));
                Event event = eventRepository.findById(request.getEventId())
                                .orElseThrow(() -> new EventNotFoundException(
                                                "Event not found : " + request.getEventId()));
                TicketType ticketType = ticketTypeRepository.findById(request.getTicketTypeId())
                                .orElseThrow(() -> new TicketTypeException("Ticket Type not found : "));
                if (ticketType.getRemainingQuantity() < request.getQuantity()) {
                        throw new IllegalStateException("Ticket Quantity Exceeded");
                }

                PricingStrategyType strategyType = ticketType.getEvent() != null
                                ? ticketType.getEvent().getPricingStrategyType()
                                : PricingStrategyType.DEFAULT;

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

                if (existingItem != null) {
                        existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
                        existingItem.setPrice(existingItem.getQuantity() * ticketType.getCurrentPrice());
                } else {
                        CartItem newItem = CartItem.builder()
                                        .event(event)
                                        .ticketType(ticketType)
                                        .quantity(request.getQuantity())
                                        .price(request.getQuantity() * ticketType.getCurrentPrice())
                                        .cart(cart1)
                                        .build();
                        cart1.getItems().add(newItem);
                }

                // update total
                double total = cart1.getItems().stream()
                                .mapToDouble(CartItem::getPrice)
                                .sum();
                cart1.setTotalPrice(total);

                Cart saved = cartRepository.save(cart1);

                return cartMapper.toCartResponse(saved);
        }

        @Override
        @CacheEvict(value = "userCarts", key = "#userId")
        public CartResponse updateItemQuantity(Long userId, Long cartItemId, int newQuantity) {
                CartItem item = cartItemRepository.findById(cartItemId)
                                .orElseThrow(() -> new CartItemNotFound("Item not found"));

                if (!item.getCart().getUser().getId().equals(userId)) {
                        throw new IllegalStateException("User not found");
                }
                item.setQuantity(newQuantity);
                item.setPrice(newQuantity * item.getTicketType().getCurrentPrice());
                cartItemRepository.save(item);

                Cart cart = item.getCart();
                double total = cart.getItems().stream()
                                .mapToDouble(CartItem::getPrice)
                                .sum();
                cart.setTotalPrice(total);
                cart.setTotalPrice(total);
                cartRepository.save(cart);
                return cartMapper.toCartResponse(cart);
        }

        @Override
        @CacheEvict(value = "userCarts", key = "#userId")
        public CartResponse removeItemFromCart(Long userId, Long cartItemId) {
                CartItem cartItem = cartItemRepository.findByIdAndCartUserId(cartItemId, userId)
                                .orElseThrow(() -> new CartItemNotFound("Cart item not found in user's cart"));

                Cart cart = cartItem.getCart();
                cart.getItems().remove(cartItem);
                cartItemRepository.delete(cartItem);

                double total = cart.getItems().stream()
                                .mapToDouble(CartItem::getPrice)
                                .sum();
                cart.setTotalPrice(total);

                Cart saved = cartRepository.save(cart);
                return cartMapper.toCartResponse(saved);
        }

}
