package org.rishabh.eventmanagementsystemadvanced.Utils.EventListeners;

import lombok.Getter;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.EventStatus;

@Getter
public class EventPublishedEvent {
    private final Long eventId;
    private final String eventName;
    private final EventStatus eventStatus;

    public EventPublishedEvent(Long eventId, String eventName, EventStatus eventStatus) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventStatus = eventStatus;
    }
}
