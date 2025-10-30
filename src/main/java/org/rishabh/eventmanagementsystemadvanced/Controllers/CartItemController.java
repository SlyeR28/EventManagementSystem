package org.rishabh.eventmanagementsystemadvanced.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.CartResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.AddToCartRequest;
import org.rishabh.eventmanagementsystemadvanced.Services.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<CartResponse> addToCart(@PathVariable Long userId
            , @Valid @RequestBody AddToCartRequest addToCartRequest) {
        CartResponse toCart = cartItemService.addItemToCart(userId, addToCartRequest);
        return ResponseEntity.ok(toCart);
    }

    @DeleteMapping("/{userId}/remove/{itemId}")
    public ResponseEntity<CartResponse> removeFromCart(@PathVariable Long userId
            , @PathVariable Long itemId) {
        CartResponse removeItem = cartItemService.removeItemFromCart(userId, itemId);
        return ResponseEntity.ok(removeItem);
    }

    @PutMapping("/{userId}/update/{itemId}")
    public ResponseEntity<CartResponse>updateCartItem(@PathVariable Long userId ,  @PathVariable Long itemId , @RequestParam int quantity) {
        CartResponse itemQuantity = cartItemService.updateItemQuantity(userId, itemId, quantity);
        return ResponseEntity.ok(itemQuantity);
    }

}
