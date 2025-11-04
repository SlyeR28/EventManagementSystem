package org.rishabh.eventmanagementsystemadvanced.Utils.EventListeners;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TicketSalesStartedEvent extends ApplicationEvent {

    private long userId;
    private final Long eventId;
    private final String eventName;
    private final Double ticketPrice;

    public TicketSalesStartedEvent(Object source, Long userId,Long eventId, String eventName, Double ticketPrice) {
        super(source);
        this.userId = userId;
        this.eventId = eventId;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
    }
}
