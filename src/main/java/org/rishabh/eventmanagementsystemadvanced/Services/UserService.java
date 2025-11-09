package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.User;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.Role;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PagedResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.UserDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.UserRequest;
import org.springframework.data.domain.Page;

public interface UserService {

    UserDto createUser(UserRequest userRequest);
    boolean activateUser(String activationToken);
    UserDto updateUser(Long userId ,  UserRequest userRequest);
    UserDto getUserById(Long id);
    PagedResponse<UserDto> getAllUsers(int page ,  int size , String sortBy , String sortDir);
    boolean isAccountActivated(String email);
    void deleteUser(Long id);
    PagedResponse<UserDto> getAllUsersByRole(Role role, int page , int size , String sortBy , String sortDir);


    User getCurrentUser();
}
