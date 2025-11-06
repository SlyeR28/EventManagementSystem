package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.rishabh.eventmanagementsystemadvanced.Config.RazorPayConfig;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.OrderStatus;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PaymentProviders;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PaymentStatus;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PayamentVerficationResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PaymentRefundResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PaymentResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.PaymentRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.OrderRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.PaymentRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.PaymentGateWay;
import org.rishabh.eventmanagementsystemadvanced.Services.TicketService;
import org.rishabh.eventmanagementsystemadvanced.Utils.SignatureUtil;
import org.springframework.stereotype.Service;

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
                    .status(PaymentStatus.CREATED)
                    .providerPayload(order.toString())
                    .build();
        } catch (RazorpayException e) {
            throw new RuntimeException("Razorpay Exception", e);
        }
    }

    @Override
    public PayamentVerficationResponse verifyPayment(Long orderId, Long paymentId) {
        try {
            Payment rzpPayment = razorpayClient.payments.fetch(paymentId.toString());
            String status = rzpPayment.get("status");
            boolean success = "captured".equalsIgnoreCase(status) || "authorized".equalsIgnoreCase(status);
            return new PayamentVerficationResponse(success, status);
        } catch (RazorpayException e) {
            return new PayamentVerficationResponse(false, "ERROR");
        }
    }

    @Override
    public void handleWebhook(String payload, Map<String, String> headers) {
        try {
            JSONObject json = new JSONObject(payload);
            String event = json.getString("event");

            JSONObject paymentEntity = json
                    .getJSONObject("payload")
                    .getJSONObject("payment")
                    .getJSONObject("entity");

            String providerPaymentId = paymentEntity.getString("id");
            String providerOrderId = paymentEntity.getString("order_id");
            String status = paymentEntity.getString("status");

            String signature = headers.get("x-razorpay-signature");
            String secret = razorPayConfig.getSecret();
            boolean isValid = SignatureUtil.verifyRazorPaySignature(payload, signature, secret);

            if (!isValid) {
                log.error("❌ Invalid webhook signature for Razorpay webhook");
                throw new RuntimeException("Invalid webhook signature");
            }

            if ("payment.captured".equalsIgnoreCase(event) && "captured".equalsIgnoreCase(status)) {
                org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Payment payment =
                        paymentRepository.findByProviderOrderId(providerOrderId)
                                .orElseThrow(() -> new RuntimeException("Payment not found for providerOrderId: " + providerOrderId));

                payment.setProviderPaymentId(providerPaymentId);
                payment.setPaymentStatus(PaymentStatus.SUCCESS);
                paymentRepository.save(payment);

                org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Order order = payment.getOrder();
                order.setStatus(OrderStatus.CONFIRMED);
                order.setProviderOrderId(providerOrderId);
                order.setProviderPaymentId(providerPaymentId);
                orderRepository.save(order);

                ticketService.generateTickets(order.getId());
                log.info("✅ Payment confirmed via webhook for Order ID {}", order.getId());
            }
        } catch (Exception e) {
            log.error("⚠️ Webhook handling failed", e);
            throw new RuntimeException("Webhook handling failed", e);
        }
    }

    @Override
    public PaymentRefundResponse refundPayment(Long paymentId, Double amount) {
        return null;
    }
}
