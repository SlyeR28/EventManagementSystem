package org.rishabh.eventmanagementsystemadvanced.Utils.EventListeners.EventLifecycle;

import lombok.RequiredArgsConstructor;
import org.rishabh.eventmanagementsystemadvanced.Domains.Entity.User;
import org.rishabh.eventmanagementsystemadvanced.PayLoad.Request.NotificationRequest;
import org.rishabh.eventmanagementsystemadvanced.Repository.UserRepository;
import org.rishabh.eventmanagementsystemadvanced.Services.impl.NotificationService;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        int page = 0;
        int size = 1000;
        Page<User> users;

        do {
            users = userRepository.findAll(PageRequest.of(page, size));
            users.getContent().forEach(user -> {
                NotificationRequest req = NotificationRequest.builder()
                        .userId(user.getId())
                        .userEmail(user.getEmail())
                        .subject("🎟 Ticket Sales Now Open!")
                        .message("Hi " + user.getFullName() +
                                ", tickets for \"" + event.getEventName() + "\" are now available at ₹" + "very low price"+
                                ". Hurry up and grab yours before they're gone!")
                        .templateCode("TICKET_SALES_STARTED")
                        .build();

                notificationService.sendNotification(req);
            });
            page++;
        } while (users.hasNext());
    }

    @Async("notifExecutor")
    @EventListener
    public void handleEventDraftCreated(EventDraftCreatedEvent event) {
        int page = 0;
        int size = 1000; // adjust batch size as per server capacity
        Page<User> usersPage;


        do {
            usersPage = userRepository.findByRoleIn(
                    List.of("STAFF", "ORGANIZER", "ADMIN"),
                    PageRequest.of(page, size)
            );

            for (User user : usersPage.getContent()) {
                NotificationRequest req = NotificationRequest.builder()
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

            page++;
        } while (usersPage.hasNext());
    }

    @Async("notifExecutor")
    @EventListener
    public void handleEventPublished(EventPublishedEvent event) {
        int page = 0;
        int size = 1000;
        Page<User> users;

        do {
            users = userRepository.findAll(PageRequest.of(page, size));
            users.getContent().forEach(user -> {
                NotificationRequest req = NotificationRequest.builder()
                        .userId(user.getId())
                        .userEmail(user.getEmail())
                        .subject("🚀 New Event Published!")
                        .message("Hi " + user.getFullName() +
                                ", a new event \"" + event.getEventName() + "\" is now live.")
                        .templateCode("EVENT_PUBLISHED")
                        .build();
                notificationService.sendNotification(req);
            });
            page++;
        } while (users.hasNext());
    }

}
