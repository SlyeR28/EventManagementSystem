package org.rishabh.eventmanagementsystemadvanced.PayLoad.Request;

import lombok.Data;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PaymentMethod;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PaymentProviders;

@Data
public class PaymentRequest {

    private Long orderId;
    private Double amount; // in major currency units (e.g., INR)
    private String currency; // e.g., "INR"
    private String description;
    private String email;
    private PaymentProviders  paymentProviders;
}
