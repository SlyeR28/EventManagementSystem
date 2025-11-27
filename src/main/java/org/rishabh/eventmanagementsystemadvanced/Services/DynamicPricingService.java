package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.TicketType;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PricingStrategyType;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.TicketTypeDto;
import org.springframework.transaction.annotation.Transactional;

public interface DynamicPricingService {

    @Transactional
    TicketTypeDto applyPricing(Long ticketTypeId, PricingStrategyType strategyType);
}
