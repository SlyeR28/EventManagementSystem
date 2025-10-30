package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.TicketType;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PricingStrategyType;
import org.rishabh.eventmanagementsystemadvanced.Repository.TicketTypeRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.DynamicPricingService;
import org.rishabh.eventmanagementsystemadvanced.Utils.DynamicPricingEngine.DynamicPriceEngine;
import org.rishabh.eventmanagementsystemadvanced.Utils.DynamicPricingEngine.DynamicPricingFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DynamicPricingServiceImpl implements DynamicPricingService {

    private final TicketTypeRepository  ticketTypeRepository;
    private final DynamicPricingFactory dynamicPricingFactory;


    @Transactional
    @Override
    public TicketType applyPricing(Long ticketTypeId, PricingStrategyType strategyType){
        TicketType ticketType = ticketTypeRepository.findById(ticketTypeId)
                .orElseThrow(() -> new RuntimeException("Ticket Type Not Found"));
         DynamicPriceEngine engine = dynamicPricingFactory.getStrategy(strategyType);
        engine.applyDynamicPricing(ticketType);
       return ticketTypeRepository.save(ticketType);

    }

}
