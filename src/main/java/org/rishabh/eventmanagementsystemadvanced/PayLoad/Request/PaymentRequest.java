package org.rishabh.eventmanagementsystemadvanced.PayLoad.Request;

import lombok.Data;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PaymentMethod;

@Data
public class PaymentRequest {

    private Long orderId;
    private PaymentMethod paymentMethod;
}
