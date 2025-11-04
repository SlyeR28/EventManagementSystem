package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.ChannelType;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.NotficationRequest;

public interface NotificationChannel {

    ChannelType channelType();
    void send(NotficationRequest notfication);
}
