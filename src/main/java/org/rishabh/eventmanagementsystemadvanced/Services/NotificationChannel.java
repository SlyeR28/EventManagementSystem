package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.ChannelType;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.NotificationRequest;

public interface NotificationChannel {

    ChannelType channelType();
    void send(NotificationRequest notfication);
}
