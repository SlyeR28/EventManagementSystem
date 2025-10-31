package org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto;

import lombok.Builder;
import lombok.Data;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PaymentStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {

    private Long paymentId;
    private String transactionId;
    private Long orderId;
    private Double amount;
    private String paymentMethod;
    private PaymentStatus status;
    private LocalDateTime paymentDate;
}
