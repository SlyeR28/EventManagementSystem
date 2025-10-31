package org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {

    private Long ticketId;
    private String ticketTypeName;
    private String purchaserName;
    private Double priceAtPurchase;
    private String status;
    private LocalDateTime purchasedAt;
}
