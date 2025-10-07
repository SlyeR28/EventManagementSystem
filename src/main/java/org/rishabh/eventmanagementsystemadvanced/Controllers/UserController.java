package org.rishabh.eventmanagementsystemadvanced.Controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.AuthResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.UserDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.AuthRequest;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.UserRequest;
import org.rishabh.eventmanagementsystemadvanced.Security.Jwt.JwtUtils;
import org.rishabh.eventmanagementsystemadvanced.Security.Services.CustomUserDetailsService;
import org.rishabh.eventmanagementsystemadvanced.Services.UserService;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/register")
    public ResponseEntity<UserDto>registerUser(@RequestBody UserRequest user){
        UserDto user1 = userService.createUser(user);
        return  ResponseEntity.ok(user1);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse>login(@RequestBody AuthRequest authRequest){
        Authentication authenticate = this.authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        List<String> roles = authenticate.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(authRequest.getEmail());
        String token = this.jwtUtils.generateToken(userDetails.getUsername(), roles);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setUsername(userDetails.getUsername());
        authResponse.getLoginDate();
          return ResponseEntity.ok().body(authResponse);
    }

}
