package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.AuthResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.AuthRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.UserRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.AuthService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl  implements AuthService {

    private final UserRepository userRepository;


    @Override
    public AuthResponse login(AuthRequest authRequest) {
     return null;
    }
}
