package org.rishabh.eventmanagementsystemadvanced.Controllers;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.UserDto;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.UserRequest;
import org.rishabh.eventmanagementsystemadvanced.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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



    //update profile
    //get single profile
    // getAll profile by pagination



}
