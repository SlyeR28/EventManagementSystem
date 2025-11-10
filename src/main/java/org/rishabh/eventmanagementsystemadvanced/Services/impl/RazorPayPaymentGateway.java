package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.rishabh.eventmanagementsystemadvanced.Config.RazorPayConfig;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Payment;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PaymentProviders;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PaymentStatus;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PaymentRefundResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PaymentResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.PaymentRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.OrderRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.PaymentRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.PaymentGateWay;
import org.rishabh.eventmanagementsystemadvanced.Services.TicketService;
import org.rishabh.eventmanagementsystemadvanced.Utils.SignatureUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Service("razorpay")
@RequiredArgsConstructor
public class RazorPayPaymentGateway implements PaymentGateWay {

    private final PaymentRepository paymentRepository;
    private final RazorPayConfig razorPayConfig;
    private RazorpayClient razorpayClient;
    private final OrderRepository orderRepository;
    private final TicketService ticketService;

    @PostConstruct
    public void init() throws RazorpayException {
        razorpayClient = new RazorpayClient(razorPayConfig.getKey(), razorPayConfig.getSecret());
    }

    @Override
    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        try {
            JSONObject orderRequest = new JSONObject();
            long amountInPaise = Math.round(paymentRequest.getAmount() * 100);
            orderRequest.put("amount", amountInPaise);
            orderRequest.put("currency", paymentRequest.getCurrency() == null ? "INR" : paymentRequest.getCurrency());
            orderRequest.put("receipt", String.valueOf(paymentRequest.getOrderId()));
            orderRequest.put("payment_capture", 1);

            Order order = razorpayClient.orders.create(orderRequest);

            return PaymentResponse.builder()
                    .orderId(paymentRequest.getOrderId())
                    .providerOrderId(order.get("id"))
                    .amount(paymentRequest.getAmount())
                    .paymentProviders(PaymentProviders.RAZORPAY)
                    .paymentStatus(PaymentStatus.CREATED)
                    .paymentDate(LocalDateTime.now())
                    .build();
        } catch (RazorpayException e) {
            throw new RuntimeException("Razorpay create order failed", e);
        }
    }

    @Override
    public boolean verifyPayment(String providerOrderId, String providerPaymentId, String signature) {
        try {
            String payload = providerOrderId + "|" + providerPaymentId;
            boolean verified = SignatureUtil.verifyRazorPaySignature(signature, payload, razorPayConfig.getSecret());

            if (verified) {
                Payment payment = paymentRepository.findByProviderOrderId(providerOrderId)
                        .orElseThrow(() -> new RuntimeException("Payment not found for providerOrderId: " + providerOrderId));

                payment.setProviderPaymentId(providerPaymentId);
                payment.setPaymentStatus(PaymentStatus.SUCCESS);
                paymentRepository.save(payment);

                log.info("✅ Razorpay signature verified for providerOrderId {}", providerOrderId);
            }

            return verified;
        } catch (Exception e) {
            log.error(" Razorpay verification failed", e);
            return false;
        }
    }

    @Override
    public void handleWebhook(String payload, Map<String, String> headers) {
        try {
            String signature = headers.get("x-razorpay-signature");
            boolean valid = SignatureUtil.verifyRazorPaySignature(signature, payload, razorPayConfig.getSecret());

            if (!valid) {
                log.error(" Invalid Razorpay webhook signature");
                return;
            }

            JSONObject json = new JSONObject(payload);
            String event = json.getString("event");
            if (!"payment.captured".equalsIgnoreCase(event)) return;

            JSONObject paymentEntity = json.getJSONObject("payload").getJSONObject("payment").getJSONObject("entity");
            String providerOrderId = paymentEntity.getString("order_id");
            String providerPaymentId = paymentEntity.getString("id");
            String status = paymentEntity.getString("status");

            if (!"captured".equalsIgnoreCase(status)) return;

            Payment payment = paymentRepository.findByProviderOrderId(providerOrderId)
                    .orElseThrow(() -> new RuntimeException("Payment not found for providerOrderId: " + providerOrderId));

            // Only update PaymentStatus
            payment.setProviderPaymentId(providerPaymentId);
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment);

            log.info(" Razorpay webhook processed: PaymentStatus updated for providerOrderId {}", providerOrderId);

        } catch (Exception e) {
            log.error("Razorpay webhook processing failed", e);
        }
    }

    @Override
    public PaymentRefundResponse refundPayment(Long paymentId, Double amount) {
        return null;
    }
}
