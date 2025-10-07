package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.User;
import org.rishabh.eventmanagementsystemadvanced.Mapper.UserMapper;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.UserDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.UserRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.UserRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserSerivceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final MailService mailService;


    @Override
    public UserDto createUser(UserRequest userRequest) {
        User entity = userMapper.toEntity(userRequest);
        entity.setActivationCode(UUID.randomUUID().toString());
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        User saved = userRepository.save(entity);
        String activationLink = "http://localhost:8080/api/v1/activation?token="  + saved.getActivationCode();
        String subject = "Activate your Event Manager Account ";
        String body = "Click on the following link to activate your account : " + activationLink;
        mailService.sendMail(entity.getEmail(), subject, body);
        return  userMapper.toDto(saved);
    }
}
