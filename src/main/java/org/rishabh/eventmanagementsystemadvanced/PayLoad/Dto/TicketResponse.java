package org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.TicketStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {

     private Long id;
    private String ticketTypeName;
    private Double priceAtPurchase;
    private String purchaserName;
    private TicketStatus status;
    private String qrCodeUrl;
    private LocalDateTime purchasedAt;
    private List<ValidationResponse> validations;
}
