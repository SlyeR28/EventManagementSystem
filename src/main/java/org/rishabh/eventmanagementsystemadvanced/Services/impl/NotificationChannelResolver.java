package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.ChannelType;
import org.rishabh.eventmanagementsystemadvanced.Services.NotificationChannel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class NotificationChannelResolver {

    private final Map<ChannelType, NotificationChannel> channelsMap;

    public NotificationChannelResolver(List<NotificationChannel> channels) {
        // Collect all NotificationChannel implementations into a map for O(1) lookup
        this.channelsMap = channels.stream()
                .collect(Collectors.toMap(NotificationChannel::channelType, Function.identity()));
    }

    public NotificationChannel resolve(ChannelType type) {
        // Use map lookup for resolution
        NotificationChannel channel = channelsMap.get(type);
        if (channel == null) {
            throw new IllegalArgumentException("ChannelType not found: " + type);
        }
        return channel;
    }

}
