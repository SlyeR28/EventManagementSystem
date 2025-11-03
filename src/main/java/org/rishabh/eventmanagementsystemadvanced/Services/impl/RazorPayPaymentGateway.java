package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.SignatureUtils;
import org.json.JSONObject;
import org.rishabh.eventmanagementsystemadvanced.Config.RazorPayConfig;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PaymentProviders;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PaymentStatus;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PayamentVerficationResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PaymentRefundResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PaymentResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.PaymentRequest;
import org.rishabh.eventmanagementsystemadvanced.Services.PaymentGateWay;
import org.rishabh.eventmanagementsystemadvanced.Utils.SignatureUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("razorpay")
@RequiredArgsConstructor
public class RazorPayPaymentGateway implements PaymentGateWay {

    private final RazorPayConfig  razorPayConfig;
    private  RazorpayClient  razorpayClient;

    @PostConstruct
    public void init() throws RazorpayException {
        razorpayClient = new RazorpayClient(razorPayConfig.getKey() ,razorPayConfig.getSecret());
    }

    @Override
    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        try{
            JSONObject orderRequest = new JSONObject();
            long amountInPaise = Math.round(paymentRequest.getAmount()*100);
            orderRequest.put("amount" , amountInPaise);
            orderRequest.put("currency" , paymentRequest.getCurrency() == null ? "INR" :paymentRequest.getCurrency());
            orderRequest.put("receipt" , String.valueOf(paymentRequest.getOrderId()));
            orderRequest.put("payment_capture" , 1); // auto capture

            Order order = razorpayClient.orders.create(orderRequest);

            return PaymentResponse.builder()
                    .transactionId(order.get("id"))
                    .orderId(paymentRequest.getOrderId())
                    .amount(paymentRequest.getAmount())
                    .paymentProviders(PaymentProviders.RAZORPAY)
                    .status(PaymentStatus.CREATED)
                    .providerPayload(orderRequest.toString())
                    .build();

        } catch (RazorpayException e) {
            throw new RuntimeException("Razorpay Exception", e);
        }
    }

    @Override
    public PayamentVerficationResponse verifyPayment(Long orderId, Long paymentId) {
       try{
           Payment rzpPayment = razorpayClient.payments.fetch(paymentId.toString());
           String status = rzpPayment.get("status");
           boolean success = "captured".equalsIgnoreCase(status) || "authorized".equalsIgnoreCase(status);
           return new PayamentVerficationResponse(success , status);
       } catch (RazorpayException e) {
           return new PayamentVerficationResponse(false , "ERROR");
       }
    }

    @Override
    public PaymentRefundResponse refundPayment(Long paymentId, Double amount) {
        return null;
    }

    @Override
    public void handleWebhook(String payload, Map<String, String> headers) {
        String signature = headers.getOrDefault("x-razorPay-signature", headers.get("x-razorPay-signature"));
        String secret = razorPayConfig.getSecret();
        boolean verifySignature = SignatureUtil.verifyRazorPaySignature(payload, signature, secret);
        if(!verifySignature){
            throw new RuntimeException("Invalid payment signature");
        }
        System.out.println("RazorPay webhook validated:.." +payload);

    }
}
