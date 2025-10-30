package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.CartResponse;

public interface CartService {


    CartResponse viewCart(Long userId);

    void clearCart(Long userId);
}
