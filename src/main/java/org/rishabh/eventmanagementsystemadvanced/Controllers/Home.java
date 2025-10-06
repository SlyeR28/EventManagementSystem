package org.rishabh.eventmanagementsystemadvanced.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/vi")
public class Home {

    @GetMapping("/")
    public String home(){
        return "Home Page";
    }
}
