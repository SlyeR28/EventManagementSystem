package org.rishabh.eventmanagementsystemadvanced.Utils.EventListeners.EventLifecycle;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UpdatedEvent  extends ApplicationEvent {

    private final Long eventId;
    private final String eventName;

    public UpdatedEvent(Object source, Long eventId, String eventName) {
        super(source);
        this.eventId = eventId;
        this.eventName = eventName;
    }
}
