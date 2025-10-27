package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.TicketType;
import org.rishabh.eventmanagementsystemadvanced.Services.DynamicPriceEngine;
import org.springframework.stereotype.Component;

@Component("demandBased")
public class DemandBasedPricingEngine implements DynamicPriceEngine {

    @Override
    public void applyDynamicPricing(TicketType ticketType) {
        double solidRatio = (double) (ticketType.getTotalAvailable()
                           -ticketType.getRemainingQuantity())/(ticketType.getTotalAvailable());

        if(solidRatio > 0.80){
            ticketType.setPrice(ticketType.getPrice() * 1.30);
        }else if(solidRatio > 0.50){
            ticketType.setPrice(ticketType.getPrice() * 0.50);
        }
    }
}
