package org.rishabh.eventmanagementsystemadvanced.Utils.DynamicPricingEngine;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.TicketType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Component("timeBased")
public class TimeBasedPricingEngine implements DynamicPriceEngine {

    @Override
    public void applyDynamicPricing(TicketType ticketType) {

         if(ticketType.getEvent() == null || ticketType.getEvent().getStartTime()==null)return;


        LocalDateTime eventStart = ticketType.getEvent().getStartTime();
        long hoursLeft = java.time.Duration.between(LocalDateTime.now(), eventStart).toHours();

        if(hoursLeft >= 24){

        }else if(hoursLeft >= 6){

        }
    }
}
