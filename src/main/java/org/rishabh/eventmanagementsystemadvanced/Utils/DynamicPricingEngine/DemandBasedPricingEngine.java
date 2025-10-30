package org.rishabh.eventmanagementsystemadvanced.Utils.DynamicPricingEngine;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.TicketType;
import org.springframework.stereotype.Component;

@Component("demandBased")
public class DemandBasedPricingEngine implements DynamicPriceEngine {

    @Override
    public void applyDynamicPricing(TicketType ticketType) {
        double soldRatio = (double) (ticketType.getTotalQuantity() - ticketType.getRemainingQuantity())
                / ticketType.getTotalQuantity();

        double newPrice = ticketType.getBasePrice();

        if (soldRatio > 0.90) {
            newPrice *= 1.50;
        } else if (soldRatio > 0.75) {
            newPrice *= 1.30;
        } else if (soldRatio > 0.50) {
            newPrice *= 1.15;
        } else if (soldRatio > 0.25) {
            newPrice *= 1.05;
        }

        ticketType.setCurrentPrice(newPrice);
    }
}
