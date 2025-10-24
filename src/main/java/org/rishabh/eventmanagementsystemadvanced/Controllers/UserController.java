package org.rishabh.eventmanagementsystemadvanced.Controllers;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.Role;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ApiResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.UserDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.UserRequest;
import org.rishabh.eventmanagementsystemadvanced.Services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserDto>registerUser(@RequestBody UserRequest user){
        UserDto user1 = userService.createUser(user);
        return  ResponseEntity.ok(user1);
    }


    @GetMapping("/activation")
    public ResponseEntity<String>activateUser(@RequestParam("token") String token){
        boolean isActivated = userService.activateUser(token);
        if(isActivated){
            return ResponseEntity.ok("your account has been activated successfully");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Activation Token Not Found or Expired");
        }
    }

    @PutMapping("/update/{id}")
   public ResponseEntity<UserDto>updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest){
        boolean accountActivated = userService.isAccountActivated(userRequest.getEmail());
        if(!accountActivated){
           throw  new UsernameNotFoundException("User not found with id " + id);
        }else {
            UserDto updated = userService.updateUser(id, userRequest);
            return ResponseEntity.ok(updated);
        }
    }

     @GetMapping("/single/{id}")
     public ResponseEntity<UserDto>getUser(@PathVariable Long id){
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
     }


     @GetMapping("/")
    public ResponseEntity<Page<UserDto>>getAllUsers(
            @RequestParam(defaultValue = "0" , required = false)int page,
            @RequestParam(defaultValue = "10" , required = false)int size,
            @RequestParam(defaultValue = "id" , required = false)String sortBy,
            @RequestParam(defaultValue = "asc" , required = false)String sortDir
     ){
         Page<UserDto> user = userService.getAllUsers(page , size , sortBy , sortDir);
         return  ResponseEntity.ok(user);
     }

    @GetMapping("/filter")
    public ResponseEntity<Page<UserDto>>getAllUsersByRoles(
            @RequestParam Role role,
            @RequestParam(defaultValue = "0" , required = false)int page,
            @RequestParam(defaultValue = "10" , required = false)int size,
            @RequestParam(defaultValue = "id" , required = false)String sortBy,
            @RequestParam(defaultValue = "asc" , required = false)String sortDir
    ) {

        Page<UserDto> usersByRole = userService.getAllUsersByRole(role, page, size, sortBy, sortDir);
        return  ResponseEntity.ok(usersByRole);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<ApiResponse>deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("User deleted successfully");
        return ResponseEntity.ok(apiResponse);

    }

}
