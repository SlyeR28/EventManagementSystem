package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.CartItemResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.CartResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.AddToCartRequest;

import java.util.List;

public interface CartItemService {
     CartResponse addItemToCart(Long userId, AddToCartRequest request );
    CartResponse updateItemQuantity(Long userId ,Long cartItemId , int newQuantity);
   CartResponse removeItemFromCart(Long userId ,Long cartItemId);



}
