package org.rishabh.eventmanagementsystemadvanced.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.AuthResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.AuthRequest;
import org.rishabh.eventmanagementsystemadvanced.Security.Jwt.JwtUtils;
import org.rishabh.eventmanagementsystemadvanced.Security.Services.CustomUserDetails;
import org.rishabh.eventmanagementsystemadvanced.Security.Services.CustomUserDetailsService;
import org.rishabh.eventmanagementsystemadvanced.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager  authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtils  jwtUtils;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse>login(@Valid @RequestBody AuthRequest authRequest) {

        if (!userService.isAccountActivated(authRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (authRequest.getEmail(), authRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            List<String> rolesAuthorities = authentication.getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority).toList();
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getEmail());
            Map<String, Object> claims = new HashMap<>();
            claims.put("rolesAuthorities", rolesAuthorities);
            String token = jwtUtils.generateToken(claims, userDetails);
            AuthResponse authResponse = new AuthResponse();
            authResponse.setEmail(userDetails.getUsername());
            authResponse.setToken(token);

            // --- ADD THIS BLOCK ---
// Assuming your CustomUserDetails has the ID, or you fetch the user entity
// If userDetails is an instance of your User entity or wraps it:
// authResponse.setUserId(((CustomUserDetails) userDetails).getId());

// OR if you need to fetch it:
// User user = userService.getUserByEmail(authRequest.getEmail());
// authResponse.setUserId(user.getId());
// ----------------------

            if (userDetails instanceof CustomUserDetails) {
                authResponse.setUserId(((CustomUserDetails) userDetails).getId());
            }

            return ResponseEntity.ok(authResponse);
        }
    }


    //logout
    //password change
    //forget password
    //delete account
 



}
