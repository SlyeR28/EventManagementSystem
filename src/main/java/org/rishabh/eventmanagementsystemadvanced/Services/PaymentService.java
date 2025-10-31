package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PaymentResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.PaymentRequest;

public interface PaymentService {

    PaymentResponse makePayment(PaymentRequest paymentRequest);
    PaymentResponse getPaymentByTransactionId(String transactionId);
}
