package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.ChannelType;
import org.rishabh.eventmanagementsystemadvanced.Services.NotificationChannel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationChannelResolver {

    private final List<NotificationChannel> channels;

    public NotificationChannel resolve(ChannelType type) {
       return channels.stream()
               .filter(c -> c.channelType() == type)
               .findFirst()
               .orElseThrow(() -> new IllegalArgumentException("ChannelType not found" + type));
    }

}
