package org.rishabh.eventmanagementsystemadvanced.Utils.EventListeners;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.User;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.NotficationRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.UserRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.impl.NotificationService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;
    private final UserRepository userRepository;


    @Async("notifExecutor")
    @EventListener
    public void handleTicketSalesStarted(TicketSalesStartedEvent event) {
        User user = userRepository.findById(event.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + event.getUserId()));
        NotficationRequest req = NotficationRequest.builder()
                .userId(event.getUserId())
                .userEmail(user.getEmail())
                .subject("🎟 Ticket Sales Now Open!")
                .message("Hi " + user.getFullName() + ", ticket sales for your event are now live. Hurry up and grab yours!")
                .templateCode("TICKET_SALES_STARTED")
                .build();

        notificationService.sendNotification(req);
    }

    @Async("notifExecutor")
    @EventListener
    public void handlePaymentCompleted(PaymentCompleted event) {
        User user = userRepository.findById(event.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + event.getUserId()));
        NotficationRequest req = NotficationRequest.builder()
                .userId(event.getUserId())
                .userEmail(user.getEmail())
                .subject("✅ Payment Successful")
                .message("Hi " + user.getFullName() + ", your payment of ₹" + event.getAmount() +
                        " for order #" + event.getOrderId() + " was successful. Enjoy your event!")
                .templateCode("PAYMENT_SUCCESS")
                .build();

        notificationService.sendNotification(req);
    }

    @Async("notifExecutor")
    @EventListener
    public void handleEventDraftCreated(EventDraftCreatedEvent event) {


        List<User> recipients = userRepository.findByRoleIn( List.of("STAFF", "ORGANIZER", "ADMIN"));


        for (User user : recipients) {
            NotficationRequest req = NotficationRequest.builder()
                    .userId(user.getId())
                    .userEmail(user.getEmail())
                    .subject("📝 New Event in Draft")
                    .message("Hi " + user.getFullName() +
                            ", a new event \"" + event.getEventName() + "\" is now in draft state. " +
                            "Please review and take necessary actions.")
                    .templateCode("EVENT_DRAFT_CREATED")
                    .build();

            notificationService.sendNotification(req);
        }
    }



}
