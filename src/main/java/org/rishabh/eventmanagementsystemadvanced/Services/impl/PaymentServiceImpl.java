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

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final Map<String, PaymentGateWay> gateways;
    private  Map<PaymentProviders , PaymentGateWay> paymentGatewayMap;
    private final PaymentRepository  paymentRepository;
    private final OrderRepository orderRepository;
    private final TicketService ticketService;

    @PostConstruct
    void init(){
        // ✅ Initialize the map first
        paymentGatewayMap = new EnumMap<>(PaymentProviders.class);

        // ✅ Populate with available gateways
        if (gateways.containsKey("razorpay")) {
            paymentGatewayMap.put(PaymentProviders.RAZORPAY, gateways.get("razorpay"));
        }
        if (gateways.containsKey("stripe")) {
            paymentGatewayMap.put(PaymentProviders.STRIPE, gateways.get("stripe"));
        }

        System.out.println("✅ Payment gateways initialized: " + paymentGatewayMap.keySet());


    }

    private PaymentGateWay getPaymentGateway(PaymentProviders paymentProvider){
        return paymentGatewayMap.getOrDefault(paymentProvider , paymentGatewayMap.get(PaymentProviders.RAZORPAY));
    }


    @Override
    public PaymentResponse makePayment(PaymentRequest paymentRequest) {
        Order order = orderRepository.findById(paymentRequest.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order Not Found"));
        PaymentProviders providers = paymentRequest.getPaymentProviders() != null
                   ? paymentRequest.getPaymentProviders()
                  : PaymentProviders.RAZORPAY;

        PaymentGateWay gateWay = getPaymentGateway(providers);
        PaymentResponse gatewayResponse  = gateWay.createPayment(paymentRequest);

        Payment payment1 = Payment.builder()
                .order(order)
                .amount(paymentRequest.getAmount())
                .paymentProviders(providers)
                .paymentStatus(PaymentStatus.CREATED)
                .transactionId(gatewayResponse.getTransactionId())
                .paymentDate(LocalDateTime.now())
                .build();

        paymentRepository.save(payment1);

        return gatewayResponse;
    }

    @Override
    public PayamentVerficationResponse verifyPayment(Long orderId, Long paymentId, String provider) {
        PaymentProviders providers = PaymentProviders.valueOf(provider.toUpperCase());
        PaymentGateWay gateWay = getPaymentGateway(providers);
        PayamentVerficationResponse response = gateWay.verifyPayment(orderId, paymentId);

        if(response.isVerified()) {
            Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new RuntimeException("Payment Not Found"));
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment);

            Order order = payment.getOrder();
            order.setStatus(OrderStatus.CONFIRMED);
            orderRepository.save(order);

            // generating the ticket
            ticketService.generateTickets(order.getId());
            log.info("🎟 Tickets generated for order {}", order.getId());


        }
        return response;
    }



    @Override
    public void handleWebhook(String provider, String payload) {
        PaymentProviders providers = PaymentProviders.valueOf(provider.toUpperCase());
        PaymentGateWay gateWay = getPaymentGateway(providers);
        gateWay.handleWebhook(payload , Map.of());

    }

    @Override
    public PaymentRefundResponse refundPayment(Long paymentId, Double amount, String provider) {
        return null;
    }
}
