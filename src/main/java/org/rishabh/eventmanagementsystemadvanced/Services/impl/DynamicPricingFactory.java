package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PricingStrategyType;
import org.rishabh.eventmanagementsystemadvanced.Services.DynamicPriceEngine;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class DynamicPricingFactory {

    private final Map<String , DynamicPriceEngine> strategies;

    public DynamicPriceEngine getStrategy(PricingStrategyType type){
        return switch (type){
            case DEMAND_BASED ->  strategies.get("demandBased");
            case TIME_BASED ->  strategies.get("timeBased");
        };
    }
}
