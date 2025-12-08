package org.rishabh.eventmanagementsystemadvanced.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ApisResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.AuthResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.AuthRequest;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.ChangePasswordRequest;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.ForgetPasswordRequest;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.ResetPasswordRequest;
import org.rishabh.eventmanagementsystemadvanced.Security.Services.CustomUserDetails;
import org.rishabh.eventmanagementsystemadvanced.Services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService  authService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse>login(@Valid @RequestBody AuthRequest authRequest) {
       return ResponseEntity.ok(authService.login(authRequest));

    }

    @PostMapping("/change-password")
    public ResponseEntity<ApisResponse> changePassword(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(userDetails.getId(), request);
        return ResponseEntity.ok(new ApisResponse("Password changed successfully"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApisResponse> forgotPassword(@Valid @RequestBody ForgetPasswordRequest request) {
        authService.forgetPassword(request);
        return ResponseEntity.ok(new ApisResponse("Password reset link sent to your email"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApisResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok(new ApisResponse("Password reset successfully"));
    }

}
