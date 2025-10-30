package org.rishabh.eventmanagementsystemadvanced.Controllers;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.TicketType;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PricingStrategyType;
import org.rishabh.eventmanagementsystemadvanced.Services.DynamicPricingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pricing")
@RequiredArgsConstructor
public class DynamicPricingController {


    private final DynamicPricingService dynamicPricingService;

    @PostMapping("/apply/{ticketTypeId}")
    public ResponseEntity<TicketType> applyPricing(
            @PathVariable Long ticketTypeId,
            @RequestParam PricingStrategyType strategy) {

        TicketType updated = dynamicPricingService.applyPricing(ticketTypeId, strategy);
        return ResponseEntity.ok(updated);
    }
}
