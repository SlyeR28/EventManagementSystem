package org.rishabh.eventmanagementsystemadvanced.Utils.EventListeners.CartLifeCycle;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.User;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.NotificationRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.UserRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.impl.NotificationService;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor

public class NotificationSalesThresholdListener {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @Async("notifExecutor")
    @EventListener
    public void handleTicketSalesThreshold(TicketSalesThresholdEvent event) {


        // Variables needed for the "Book Fast" template
        Map<String, Object> eventVars = Map.of(
                "eventName", event.getEventName(),
                "salesPercentage", String.format("%.0f%%", event.getSalesPercentage() * 100),
                "currentPrice", event.getCurrentPrice().toString(),
                "ticketsRemaining", event.getTicketRemaining()
        );

        // Batch processing to notify potentially thousands of users
        int page = 0;
        int size = 1000;
        Page<User> users;

        do {

            users = userRepository.findAll(PageRequest.of(page, size));
            users.getContent().forEach(user -> {

                // Build the notification request (fullName will be added by the service/helper)
                NotificationRequest req = NotificationRequest.builder()
                        .userId(user.getId())
                        .userEmail(user.getEmail())
                        .templateCode("TICKET_SALES_THRESHOLD") // ⭐ Template code for the "Book Fast" message
                        .variables(eventVars)
                        .build();

                notificationService.sendNotification(req);
            });
            page++;
        } while (users.hasNext());


    }
}