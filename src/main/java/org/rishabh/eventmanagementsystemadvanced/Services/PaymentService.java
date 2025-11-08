package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PayamentVerficationResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PaymentRefundResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PaymentResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.PaymentRequest;

import java.util.Map;

public interface PaymentService {

    PaymentResponse makePayment(PaymentRequest paymentRequest);

    PayamentVerficationResponse verifyPayment(Long orderId, String providerPaymentId, String providerSignature, String provider);

    PaymentRefundResponse refundPayment(Long paymentId, Double amount, String provider);

    void handleWebhook(String provider, String payload, Map<String, String> headers);
}
