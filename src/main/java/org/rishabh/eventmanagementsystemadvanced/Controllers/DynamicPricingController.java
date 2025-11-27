package org.rishabh.eventmanagementsystemadvanced.Controllers;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.TicketType;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PricingStrategyType;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.TicketTypeDto;
import org.rishabh.eventmanagementsystemadvanced.Services.DynamicPricingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pricing")
@RequiredArgsConstructor
public class DynamicPricingController {


    private final DynamicPricingService dynamicPricingService;

    @PostMapping("/apply/{ticketTypeId}")
    public ResponseEntity<TicketTypeDto> applyPricing(
            @PathVariable Long ticketTypeId,
            @RequestParam PricingStrategyType strategy) {

        TicketTypeDto updated = dynamicPricingService.applyPricing(ticketTypeId, strategy);
        return ResponseEntity.ok(updated);
    }
}
