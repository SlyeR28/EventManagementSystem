package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PayamentVerficationResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PaymentRefundResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PaymentResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.PaymentRequest;

import java.util.Map;

public interface PaymentGateWay {
    PaymentResponse createPayment(PaymentRequest paymentRequest);
    PayamentVerficationResponse verifyPayment(Long orderId , Long paymentId);
    PaymentRefundResponse refundPayment(Long paymentId , Double amount);

    void handleWebhook(String payload , Map<String, String> headers);
}
