package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Cart;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.CartResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.AddToCartRequest;

public interface CartService {

    CartResponse addToCart( Long userId ,AddToCartRequest request);
    CartResponse viewCart(Long userId);
    CartResponse removeItem(Long userId , Long itemId);
    void clearCart(Long userId);
}
