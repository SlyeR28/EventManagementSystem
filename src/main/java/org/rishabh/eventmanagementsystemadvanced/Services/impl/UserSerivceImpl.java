package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.User;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.Role;
import org.rishabh.eventmanagementsystemadvanced.Mapper.UserMapper;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.UserDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.UserRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.UserRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
        String activationLink = "http://localhost:8080/api/v1/user/activation?token="  + saved.getActivationCode();
        String subject = "Activate your Event Manager Account ";
        String body = "Click on the following link to activate your account : " + activationLink;
        mailService.sendMail(entity.getEmail(), subject, body);
        return  userMapper.toDto(saved);
    }

    @Override
    public boolean activateUser(String activationToken) {
        return userRepository.findByActivationCode(activationToken).
                map(user -> {
                    user.setIsActive(true);
                    userRepository.save(user);
                    return true;
                }).orElse(false);
    }

    @CachePut(value = "users", key = "#result.id")
    @Override
    public UserDto updateUser(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id " + userId));
        user.setEmail(userRequest.getEmail());
        user.setFullName(userRequest.getFullName());
        User saved = userRepository.save(user);
        return  userMapper.toDto(saved);
    }

    @CacheEvict(value = "users", key = "#id")
    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id " + id));
        userRepository.delete(user);
    }

    @Cacheable(
            value = "usersByRole",
            key = "T(String).format('%s_%d_%d_%s_%s', #role, #page, #size, #sortBy, #sortDir)"
    )
    @Override
    public Page<UserDto> getAllUsersByRole(Role role, int page , int size , String sortBy , String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
                Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User> byRole = userRepository.findByRole(role, pageable);
        return byRole.map(userMapper::toDto);
    }


    @Cacheable(value = "users" , key = "#id")
    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id " + id));
        return userMapper.toDto(user);
    }

    @Override
    public Page<UserDto> getAllUsers(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
                Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<User>userPage = userRepository.findAll(pageable);
        List<UserDto> userDtos = userPage.getContent().stream().map(userMapper::toDto).toList();
        return new PageImpl<>(userDtos ,  pageable, userPage.getTotalElements());
    }

    @Override
    public boolean isAccountActivated(String email) {
        return userRepository.findByEmail(email).
                map(User::getIsActive).
                orElse(false);
    }


    @Override
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName()).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email " + authentication.getName()));
    }


}
