package org.rishabh.eventmanagementsystemadvanced.Utils.DynamicPricingEngine;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.TicketType;

public interface DynamicPriceEngine {

    /**
     * Adjusts ticket price dynamically based on strategy implementation
     * @param ticketType TicketType for which to calculate new price
     */
    void applyDynamicPricing(TicketType ticketType);

}
