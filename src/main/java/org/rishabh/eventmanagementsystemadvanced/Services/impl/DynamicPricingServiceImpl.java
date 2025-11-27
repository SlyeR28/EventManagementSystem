package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.TicketType;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PricingStrategyType;
import org.rishabh.eventmanagementsystemadvanced.Mapper.TicketTypeMapper;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.TicketTypeDto;
import org.rishabh.eventmanagementsystemadvanced.Repository.TicketTypeRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.DynamicPricingService;
import org.rishabh.eventmanagementsystemadvanced.Utils.DynamicPricingEngine.DynamicPriceEngine;
import org.rishabh.eventmanagementsystemadvanced.Utils.DynamicPricingEngine.DynamicPricingFactory;
import org.rishabh.eventmanagementsystemadvanced.Utils.EventListeners.CartLifeCycle.TicketSalesThresholdEvent;
import org.rishabh.eventmanagementsystemadvanced.Utils.EventListeners.DomainEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class DynamicPricingServiceImpl implements DynamicPricingService {

    private final TicketTypeRepository  ticketTypeRepository;
    private final DynamicPricingFactory dynamicPricingFactory;
    private final DomainEventPublisher domainEventPublisher;
    private final TicketTypeMapper  ticketTypeMapper;

    private static final double NOTIFICATION_THRESHOLD = 0.75;


    @Transactional
    @Override
    public TicketTypeDto applyPricing(Long ticketTypeId, PricingStrategyType strategyType) {
        TicketType ticketType = ticketTypeRepository.findById(ticketTypeId)
                .orElseThrow(() -> new RuntimeException("Ticket Type Not Found"));

        Double oldPrice = ticketType.getCurrentPrice();

        DynamicPriceEngine engine = dynamicPricingFactory.getStrategy(strategyType);
        engine.applyDynamicPricing(ticketType);

        if (strategyType == PricingStrategyType.DEMAND_BASED) {
            int soldQuantity = ticketType.getTotalQuantity() - ticketType.getRemainingQuantity();

            if (ticketType.getTotalQuantity() > 0) {
                double soldRatio = (double) soldQuantity / ticketType.getTotalQuantity();
                if (soldRatio >= NOTIFICATION_THRESHOLD && !ticketType.getCurrentPrice().equals(oldPrice)) {
                    if (ticketType.getEvent() != null) {

                        // 3. Publish the event with the necessary dynamic data
                        domainEventPublisher.publish(
                                new TicketSalesThresholdEvent(
                                        this,
                                        ticketType.getEvent().getId(),
                                        ticketType.getEvent().getName(),
                                        soldRatio,
                                        // Use BigDecimal for currency handling
                                        BigDecimal.valueOf(ticketType.getCurrentPrice()).setScale(2, RoundingMode.HALF_UP),
                                        ticketType.getRemainingQuantity()
                                )
                        );

                    }
                }
            }
        }

        TicketType saved = ticketTypeRepository.save(ticketType);

        return ticketTypeMapper.toResponse(saved);

    }


}
