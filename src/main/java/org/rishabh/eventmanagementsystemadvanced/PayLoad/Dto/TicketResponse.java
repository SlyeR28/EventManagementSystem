package org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.TicketStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {

     private Long id;
    private String ticketTypeName;
    private String eventName;
    private String purchaserName;
    private TicketStatus status;
    private Double priceAtPurchase;
    private String qrCodeUrl;
    private LocalDateTime purchasedAt;

}
