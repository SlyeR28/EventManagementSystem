package org.rishabh.eventmanagementsystemadvanced.Utils.DynamicPricingEngine;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.TicketType;
import org.springframework.stereotype.Component;

@Component("defaultPricing")
public class DeafultPricingEngine implements DynamicPriceEngine{
    @Override
    public void applyDynamicPricing(TicketType ticketType) {
        // Default: no dynamic adjustment, use base price
        if (ticketType.getCurrentPrice() == null || ticketType.getCurrentPrice() <= 0) {
            ticketType.setCurrentPrice(ticketType.getBasePrice());
        }
    }
}
