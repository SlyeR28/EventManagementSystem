package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cache;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Cart;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.CartItem;
import org.rishabh.eventmanagementsystemadvanced.Exception.CartNotFoundException;
import org.rishabh.eventmanagementsystemadvanced.Mapper.CartMapper;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.CartResponse;
import org.rishabh.eventmanagementsystemadvanced.Repository.CartItemRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.CartRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.CartService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;


    @Cacheable(value = "userCarts", key = "#userId")
    @Transactional(readOnly = true)
    @Override
    public CartResponse viewCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new CartNotFoundException("Cart not found" + userId));

        // Recalculate total (in case prices changed)
        double total = cart.getItems().stream()
                .mapToDouble(CartItem::getPrice)
                .sum();
        cart.setTotalPrice(total);

        return cartMapper.toCartResponse(cart);
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));
        cartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }
}
