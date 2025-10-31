package org.rishabh.eventmanagementsystemadvanced.Services.impl;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Order;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.Payment;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.OrderStatus;
import org.rishabh.eventmanagementsystemadvanced.Domains.Modal.PaymentStatus;
import org.rishabh.eventmanagementsystemadvanced.Mapper.PaymentMapper;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PaymentResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.PaymentRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.OrderRepository;
import org.rishabh.eventmanagementsystemadvanced.Repository.PaymentRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {


    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMapper  paymentMapper;


    @Override
    public PaymentResponse makePayment(PaymentRequest paymentRequest) {
        Order order = orderRepository.findById(paymentRequest.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order Not Found"));

        // Simulate payment success/failure (you can integrate Razorpay/Stripe later)
        boolean success = Math.random() > 0.2; // 80% chance success

        Payment payment = Payment.builder()
                .order(order)
                .amount(order.getTotalAmount())
                .paymentMethod(paymentRequest.getPaymentMethod())
                .transactionId(UUID.randomUUID().toString())
                .paymentDate(LocalDateTime.now())
                .paymentStatus(success ? PaymentStatus.SUCCESS:PaymentStatus.FAILED)
                .build();
        Payment save = paymentRepository.save(payment);

        order.setStatus(success ? OrderStatus.CONFIRMED : OrderStatus.CANCELLED);
        orderRepository.save(order);
        return paymentMapper.toResponse(save);
    }


    @Override
    public PaymentResponse getPaymentByTransactionId(String transactionId) {
        Payment payment = paymentRepository.findByTransactionId(transactionId);
        if (payment == null) {
            throw new RuntimeException("Payment Not Found");
        }
        return paymentMapper.toResponse(payment);
    }
}
