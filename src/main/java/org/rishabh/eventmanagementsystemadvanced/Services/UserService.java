package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.Role;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.UserDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.UserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDto createUser(UserRequest userRequest);
    boolean activateUser(String activationToken);
    UserDto updateUser(Long userId ,  UserRequest userRequest);
    UserDto getUserById(Long id);
    Page<UserDto> getAllUsers(int page ,  int size , String sortBy , String sortDir);
    boolean isAccountActivated(String email);
    void deleteUser(Long id);
    Page<UserDto>getAllUsersByRole(Role role, int page , int size ,  String sortBy , String sortDir);


}
