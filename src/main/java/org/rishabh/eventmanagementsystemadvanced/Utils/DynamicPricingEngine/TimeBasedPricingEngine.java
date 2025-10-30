package org.rishabh.eventmanagementsystemadvanced.Utils.DynamicPricingEngine;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.TicketType;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("timeBased")
public class TimeBasedPricingEngine implements DynamicPriceEngine {

    @Override
    public void applyDynamicPricing(TicketType ticketType) {

        if (ticketType.getEvent() == null || ticketType.getEvent().getStartTime() == null) return;

        LocalDateTime eventStart = ticketType.getEvent().getStartTime();
        long hoursLeft = Duration.between(LocalDateTime.now(), eventStart).toHours();

        double newPrice = ticketType.getBasePrice();

        if (hoursLeft <= 6) {
            newPrice *= 1.40; // last-minute rush
        } else if (hoursLeft <= 24) {
            newPrice *= 1.20;
        } else if (hoursLeft <= 72) {
            newPrice *= 1.10;
        }

        ticketType.setCurrentPrice(newPrice);
    }
}
