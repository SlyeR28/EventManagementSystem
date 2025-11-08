package org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto;

import lombok.Builder;
import lombok.Data;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PaymentProviders;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PaymentStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {

    private Long paymentId;
    private String transactionId;
    private Long orderId;
    private Double amount;
    private PaymentStatus paymentStatus;
    private PaymentProviders paymentProviders;
    private String providerOrderId;
    private String providerPaymentId;
    private LocalDateTime paymentDate;
}
