package org.rishabh.eventmanagementsystemadvanced.Utils.DynamicPricingEngine;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PricingStrategyType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class DynamicPricingFactory {

    private final Map<String , DynamicPriceEngine> strategies;

    public DynamicPriceEngine getStrategy(PricingStrategyType type){
        if (type == null) {
            return strategies.get("defaultPricing");
        }

        return switch (type) {
            case DEMAND_BASED -> strategies.getOrDefault("demandBased", strategies.get("defaultPricing"));
            case TIME_BASED -> strategies.getOrDefault("timeBased", strategies.get("defaultPricing"));
            default -> strategies.get("defaultPricing");
        };
    }
}
