package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.UserDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.UserRequest;

import java.util.List;

public interface UserService {

    UserDto createUser(UserRequest userRequest);
    UserDto updateUser(Long userId ,  UserRequest userRequest);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();

}
