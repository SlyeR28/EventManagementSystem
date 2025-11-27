package org.rishabh.eventmanagementsystemadvanced.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ApiResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.CartResponse;
import org.rishabh.eventmanagementsystemadvanced.Services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;



    @GetMapping("/view/{userId}")
    public ResponseEntity<CartResponse> viewCart(@PathVariable Long userId) {
        CartResponse toCart = cartService.viewCart(userId);
        return ResponseEntity.ok(toCart);
    }


    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok(new ApiResponse("Cart has been cleared"));
    }

}
