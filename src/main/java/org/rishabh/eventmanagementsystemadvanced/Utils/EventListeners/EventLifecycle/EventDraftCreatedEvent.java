package org.rishabh.eventmanagementsystemadvanced.Utils.EventListeners.EventLifecycle;

import lombok.Getter;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.EventStatus;
import org.springframework.context.ApplicationEvent;

@Getter
public class EventDraftCreatedEvent extends ApplicationEvent {
    private final Long eventId;
    private final String eventName;
    private final EventStatus eventStatus;

    public EventDraftCreatedEvent(Object source, Long eventId, String eventName , EventStatus eventStatus) {
        super(source);
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventStatus = eventStatus;
    }
}
