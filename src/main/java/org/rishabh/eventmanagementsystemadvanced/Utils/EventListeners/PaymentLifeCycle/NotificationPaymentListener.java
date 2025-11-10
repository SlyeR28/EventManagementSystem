package org.rishabh.eventmanagementsystemadvanced.Utils.EventListeners.PaymentLifeCycle;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.User;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.NotificationRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.UserRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.impl.NotificationService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class NotificationPaymentListener {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @Async("notifExecutor")
    @EventListener
    public void handlePaymentCompleted(PaymentCompleted event) {
        User user = userRepository.findById(event.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + event.getUserId()));

        Map<String, Object> templateVars = Map.of(
                "fullName", user.getFullName(),
                "amount", event.getAmount(),
                "orderId", event.getOrderId()
        );

        NotificationRequest req = NotificationRequest.builder()
                .userId(event.getUserId())
                .userEmail(user.getEmail())
//                .message("Hi " + user.getFullName() + ", your payment of ₹" + event.getAmount() +
//                        " for order #" + event.getOrderId() + " was successful. Enjoy your event!")
                .templateCode("PAYMENT_SUCCESS")
                .variables(templateVars)
                .build();

        notificationService.sendNotification(req);
    }


}
