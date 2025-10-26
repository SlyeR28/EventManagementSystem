package org.rishabh.eventmanagementsystemadvanced.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class HomeControlller {


    @GetMapping("/")
    public ResponseEntity<String>testController(){
        return ResponseEntity.ok("Hello World");
    }
}
