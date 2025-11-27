package org.rishabh.eventmanagementsystemadvanced.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ApisResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PayamentVerficationResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PaymentResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.PaymentRequest;
import org.rishabh.eventmanagementsystemadvanced.Services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping("/create/{provider}")
    public ResponseEntity<PaymentResponse> createPayment(@PathVariable String provider ,
                                                      @Valid @RequestBody PaymentRequest paymentRequest) {
        PaymentResponse payment = paymentService.makePayment(paymentRequest);
        return ResponseEntity.ok().body(payment);
    }
    @PostMapping("/webhook/{provider}")
    public ResponseEntity<ApisResponse> handleWebhook(
            @PathVariable String provider,
            @RequestBody String payload,
            @RequestHeader(value = "x-razorpay-signature", required = false) String signature
    ) {
        // Pass headers to service for secure verification
        paymentService.handleWebhook(provider, payload, Map.of("x-razorpay-signature", signature));
        return ResponseEntity.ok().body(new ApisResponse("success"));
    }

    @PostMapping("/{provider}/verify")
    public ResponseEntity<PayamentVerficationResponse> verifyPayment(
            @PathVariable String provider,
            @RequestParam Long orderId,
            @RequestParam String providerPaymentId,
            @RequestParam String providerSignature
    ) {
        PayamentVerficationResponse response =
                paymentService.verifyPayment(orderId, providerPaymentId, providerSignature, provider);
        return ResponseEntity.ok().body(response);
    }

}

