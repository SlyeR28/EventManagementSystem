package org.rishabh.eventmanagementsystemadvanced.Utils.EventListeners.CartLifeCycle; // Consider moving this to EventLifeCycle or similar

import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;

public class TicketSalesThresholdEvent extends ApplicationEvent {

    private final Long eventId;
    private final String eventName;
    private final double salesPercentage;
    private final BigDecimal currentPrice;
    private final int ticketRemaining;


    public TicketSalesThresholdEvent(Object source, Long eventId, String eventName,
                                     double salesPercentage , BigDecimal currentPrice, int ticketRemaining) {
        super(source);
        this.eventId = eventId;
        this.eventName = eventName;
        this.salesPercentage = salesPercentage;
        this.currentPrice = currentPrice;
        this.ticketRemaining = ticketRemaining;
    }


    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public Long getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public double getSalesPercentage() {
        return salesPercentage;
    }

    public int getTicketRemaining() {
        return ticketRemaining;
    }
}