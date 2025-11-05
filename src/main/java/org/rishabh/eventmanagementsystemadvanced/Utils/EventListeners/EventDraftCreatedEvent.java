package org.rishabh.eventmanagementsystemadvanced.Utils.EventListeners;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EventDraftCreatedEvent extends ApplicationEvent {
    private final Long eventId;
    private final String eventName;

    public EventDraftCreatedEvent(Object source, Long eventId, String eventName) {
        super(source);
        this.eventId = eventId;
        this.eventName = eventName;
    }
}
