package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.User;
import org.rishabh.eventmanagementsystemadvanced.Mapper.UserMapper;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.UserDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.UserRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.UserRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    public UserDto updateUser(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id " + userId));
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved = userRepository.save(user);
        return  userMapper.toDto(saved);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id " + id));
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDto).collect(Collectors.toList());
    }



    // helper method to activate user
    public boolean activateUser(String activationToken) {
        return userRepository.findByActivationCode(activationToken).map(
                user -> {
                    user.setIsActive(true);
                    userRepository.save(user);
                    return true;
                }
        ).orElse(false);
    }

    //helper method to find current user
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName()).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email " + authentication.getName()));
    }



}
