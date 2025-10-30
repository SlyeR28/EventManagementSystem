package org.rishabh.eventmanagementsystemadvanced.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ApiResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.CartResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.AddToCartRequest;
import org.rishabh.eventmanagementsystemadvanced.Services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<CartResponse> addToCart(@PathVariable Long userId
            , @Valid @RequestBody AddToCartRequest addToCartRequest) {
        CartResponse toCart = cartService.addToCart(userId, addToCartRequest);
        return ResponseEntity.ok(toCart);
    }

    @GetMapping("/view/{userId}")
    public ResponseEntity<CartResponse> viewCart(@PathVariable Long userId) {
        CartResponse toCart = cartService.viewCart(userId);
        return ResponseEntity.ok(toCart);
    }

    @DeleteMapping("/{userId}/remove/{itemId}")
    public ResponseEntity<CartResponse> removeFromCart(@PathVariable Long userId
            , @PathVariable Long itemId) {
        CartResponse removeItem = cartService.removeItem(userId, itemId);
        return ResponseEntity.ok(removeItem);
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok(new ApiResponse("Cart has been cleared"));
    }

}
