package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.User;
import org.rishabh.eventmanagementsystemadvanced.Exception.UserNotActivatedException;
import org.rishabh.eventmanagementsystemadvanced.Exception.UserNotFoundException;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.AuthResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.AuthRequest;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.ChangePasswordRequest;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.ForgetPasswordRequest;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.ResetPasswordRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.UserRepository;
import org.rishabh.eventmanagementsystemadvanced.Security.Jwt.JwtUtils;
import org.rishabh.eventmanagementsystemadvanced.Security.Services.CustomUserDetails;
import org.rishabh.eventmanagementsystemadvanced.Security.Services.CustomUserDetailsService;
import org.rishabh.eventmanagementsystemadvanced.Services.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl  implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;


    @Override
    public AuthResponse login(AuthRequest authRequest) {
        User user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(" User not found with email " + authRequest.getEmail()));

        if(!Boolean.TRUE.equals(user.getIsActive())){
            throw new UserNotActivatedException("Account is not activated");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        List<String> rolesAuthorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();

    UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getEmail());
    Map<String, Object> claims = new HashMap<>();
    claims.put("rolesAuthorities", rolesAuthorities);

    String token = jwtUtils.generateToken(claims , userDetails);

    AuthResponse authResponse = new AuthResponse();
    authResponse.setEmail(userDetails.getUsername());
    authResponse.setToken(token);

    if(userDetails instanceof CustomUserDetails){
        authResponse.setUserId(((CustomUserDetails) userDetails).getId());
    }
    authResponse.setRole(user.getRole().name());
    authResponse.setFullName(user.getFullName());
    return authResponse;
    }

    @Override
    public void changePassword(Long userId, ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id " + userId));
        if(!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())){
            throw new RuntimeException("Old password doesn't match");
        }
        if(!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())){
            throw new RuntimeException("Confirm password doesn't match");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void forgetPassword(ForgetPasswordRequest forgetPasswordRequest) {
        User user = userRepository.findByEmail(forgetPasswordRequest.getEmail()).orElseThrow(
                () -> new UserNotFoundException("User not found with email " + forgetPasswordRequest.getEmail())
        );
        String token = UUID.randomUUID().toString();
        user.setResetPasswordToken(token);
        user.setResetPasswordTokenExpiry(LocalDateTime.now().plusMinutes(5));
        userRepository.save(user);

        String resetLink = "http://localhost:5173/reset-password?token=" + token; String emailContent = "Click the link to reset your password: " + resetLink;

        mailService.sendMail(user.getEmail(), "Password Reset Request", emailContent);
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByResetPasswordToken(request.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid or expired reset token"));

        if (user.getResetPasswordTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Reset token has expired");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetPasswordToken(null);
        user.setResetPasswordTokenExpiry(null);
        userRepository.save(user);
    }
}
