package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Order;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Payment;
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
import org.rishabh.eventmanagementsystemadvanced.Services.PaymentService;
import org.rishabh.eventmanagementsystemadvanced.Services.TicketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final Map<String, PaymentGateWay> gateways;
    private Map<PaymentProviders, PaymentGateWay> paymentGatewayMap;

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final TicketService ticketService;


    @PostConstruct
    void init() {
        paymentGatewayMap = new EnumMap<>(PaymentProviders.class);
        if (gateways.containsKey("razorpay")) {
            paymentGatewayMap.put(PaymentProviders.RAZORPAY, gateways.get("razorpay"));
        }
    }

    private PaymentGateWay getPaymentGateway(PaymentProviders provider) {
        return paymentGatewayMap.getOrDefault(provider, paymentGatewayMap.get(PaymentProviders.RAZORPAY));
    }

    @Override
    public PaymentResponse makePayment(PaymentRequest paymentRequest) {
        Order order = orderRepository.findById(paymentRequest.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        paymentRepository.findByOrderId(order.getId()).ifPresent(existing -> {
            throw new RuntimeException("Payment already exists for order " + order.getId());
        });

        PaymentProviders provider = paymentRequest.getPaymentProviders() != null
                ? paymentRequest.getPaymentProviders()
                : PaymentProviders.RAZORPAY;

        PaymentGateWay gateWay = getPaymentGateway(provider);
        PaymentResponse gatewayResponse = gateWay.createPayment(paymentRequest);

        Payment payment = Payment.builder()
                .order(order)
                .amount(paymentRequest.getAmount())
                .paymentProviders(provider)
                .paymentStatus(PaymentStatus.CREATED)
                .transactionId(generateTransactionId())
                .providerOrderId(gatewayResponse.getProviderOrderId())
                .paymentDate(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);

        gatewayResponse.setPaymentId(payment.getId());
        gatewayResponse.setTransactionId(payment.getTransactionId());

        log.info("🧾 Payment created for order {} with providerOrderId {}", order.getId(), payment.getProviderOrderId());
        return gatewayResponse;
    }

    @Override
    public void handleWebhook(String provider, String payload, Map<String, String> headers) {
        PaymentProviders providers = PaymentProviders.valueOf(provider.toUpperCase());
        PaymentGateWay gateWay = getPaymentGateway(providers);
            gateWay.handleWebhook(payload, headers);
    }

    @Override
    public PayamentVerficationResponse verifyPayment(Long orderId, String providerPaymentId, String providerSignature, String provider) {
        PaymentProviders prov = PaymentProviders.valueOf(provider.toUpperCase());
        PaymentGateWay gateWay = getPaymentGateway(prov);

        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found for orderId: " + orderId));

        // ✅ Gateway verifies signature and updates PaymentStatus
        boolean verified = gateWay.verifyPayment(payment.getProviderOrderId(), providerPaymentId, providerSignature);

        if (verified && payment.getPaymentStatus() != PaymentStatus.SUCCESS) {
            Order order = payment.getOrder();
            order.setStatus(OrderStatus.CONFIRMED);
            orderRepository.save(order);


            ticketService.generateTickets(order.getId());

            log.info("🎟 Payment verified & tickets generated for order {}", order.getId());
        }

        return new PayamentVerficationResponse(verified, verified ? "SUCCESS" : "FAILED");
    }



    @Override
    public PaymentRefundResponse refundPayment(Long paymentId, Double amount, String provider) {
        return null; // future enhancement
    }

    private String generateTransactionId() {
        long ts = System.currentTimeMillis();
        int rand = ThreadLocalRandom.current().nextInt(1000, 9999);
        return "txn_" + ts + "_" + rand;
    }
}
