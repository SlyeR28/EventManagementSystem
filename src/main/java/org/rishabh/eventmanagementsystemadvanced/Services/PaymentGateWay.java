package org.rishabh.eventmanagementsystemadvanced.Services;

import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PaymentRefundResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PaymentResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.PaymentRequest;

import java.util.Map;

public interface PaymentGateWay {

    PaymentResponse createPayment(PaymentRequest paymentRequest);

    boolean verifyPayment(String providerOrderId, String providerPaymentId, String signature);



    PaymentRefundResponse refundPayment(Long paymentId , Double amount);

    void handleWebhook(String payload , Map<String, String> headers);
}
