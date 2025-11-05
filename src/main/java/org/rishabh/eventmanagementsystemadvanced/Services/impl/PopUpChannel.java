package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.ChannelType;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.NotificationRequest;
import org.rishabh.eventmanagementsystemadvanced.Services.NotificationChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PopUpChannel implements NotificationChannel {

    private final SimpMessagingTemplate simpMessagingTemplate;



    @Override
    public ChannelType channelType() {
        return ChannelType.POPUP;
    }

    @Override
    public void send(NotificationRequest req) {
        // send the popup over websocket to /topic/{userId} or /user/{userId}/queue/...
        String destination = "/topic/notifications/" + req.getUserId();
        simpMessagingTemplate.convertAndSend(destination, java.util.Map.of(
                "subject", req.getSubject(),
                "message", req.getMessage()
        ));
    }
}
