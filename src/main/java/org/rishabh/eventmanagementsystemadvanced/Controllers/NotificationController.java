package org.rishabh.eventmanagementsystemadvanced.Controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    //server application
    @MessageMapping("/sendMessage")
    @SendTo("/topic/notification")
    public String sendMessage(String message){
        System.out.println("Sending Message : " + message);
        return message;
    }

}
