package org.rishabh.eventmanagementsystemadvanced.Controllers;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.ApiResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PayamentVerficationResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Dto.PaymentResponse;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.PaymentRequest;
import org.rishabh.eventmanagementsystemadvanced.Services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping("/create/{provider}")
    public ResponseEntity<PaymentResponse> createPayment(@PathVariable String provider ,
                                                         @RequestBody PaymentRequest paymentRequest) {
        PaymentResponse payment = paymentService.makePayment(paymentRequest);
        return ResponseEntity.ok().body(payment);
    }

    @PostMapping("/webhook/{provider}")
    public ResponseEntity<ApiResponse> handleWebhook(@PathVariable String provider ,
                                                     @RequestBody String payload) {
     paymentService.handleWebhook(provider, payload);
     return ResponseEntity.ok().body(new ApiResponse("success"));
    }

    @PostMapping("/{provider}/verify")
    public ResponseEntity<PayamentVerficationResponse>verifyPayment(  @PathVariable String provider,
                                                                      @RequestParam Long orderId,
                                                                      @RequestParam Long paymentId){
        PayamentVerficationResponse response = paymentService.verifyPayment(orderId, paymentId, provider);
        return ResponseEntity.ok().body(response);
    }

}

