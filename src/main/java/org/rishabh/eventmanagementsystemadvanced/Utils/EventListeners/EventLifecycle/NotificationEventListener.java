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
import java.util.Map;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;
    private final UserRepository userRepository;


    private NotificationRequest buildNotificationRequest(User user, String templateCode, Map<String, Object> variables) {

        variables = new java.util.HashMap<>(variables);
        variables.put("fullName", user.getFullName());

        return NotificationRequest.builder()
                .userId(user.getId())
                .userEmail(user.getEmail())

                .templateCode(templateCode)
                .variables(variables)
                .build();
    }



    @Async("notifExecutor")
    @EventListener
    public void handleTicketSalesStarted(TicketSalesStartedEvent event) {
        int page = 0;
        int size = 1000;
        Page<User> users;


        Map<String, Object> eventVars = Map.of(
                "eventName", event.getEventName(),
                "ticketPrice", "very low price"
        );

        do {
            users = userRepository.findAll(PageRequest.of(page, size));
            users.getContent().forEach(user -> {
                NotificationRequest req = buildNotificationRequest(
                        user,
                        "TICKET_SALES_STARTED",
                        eventVars
                );
                notificationService.sendNotification(req);
            });
            page++;
        } while (users.hasNext());
    }


    @Async("notifExecutor")
    @EventListener
    public void handleEventDraftCreated(EventDraftCreatedEvent event) {
        int page = 0;
        int size = 1000;
        Page<User> usersPage;


        Map<String, Object> eventVars = Map.of(
                "eventName", event.getEventName()
        );

        do {

            usersPage = userRepository.findByRoleIn(
                    List.of("STAFF", "ORGANIZER", "ADMIN"),
                    PageRequest.of(page, size)
            );

            for (User user : usersPage.getContent()) {
                NotificationRequest req = buildNotificationRequest(
                        user,
                        "EVENT_DRAFT_CREATED",
                        eventVars
                );
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


        Map<String, Object> eventVars = Map.of(
                "eventName", event.getEventName()
        );

        do {
            users = userRepository.findAll(PageRequest.of(page, size));
            users.getContent().forEach(user -> {
                NotificationRequest req = buildNotificationRequest(
                        user,
                        "EVENT_PUBLISHED",
                        eventVars
                );
                notificationService.sendNotification(req);
            });
            page++;
        } while (users.hasNext());
    }


    @Async("notifExecutor")
    @EventListener
    public void handleEventCancelledEvents(EventCancelled event) {
        int page = 0;
        int size = 1000;
        Page<User> users;


        Map<String, Object> eventVars = Map.of(
                "eventName", event.getEventName()
        );

        do {

            users = userRepository.findAll(PageRequest.of(page, size));
            users.getContent().forEach(user -> {
                NotificationRequest req = buildNotificationRequest(
                        user,
                        "EVENT_CANCELLED",
                        eventVars
                );
                notificationService.sendNotification(req);
            });
            page++;
        } while (!users.isLast());
    }


    @Async("notifExecutor")
    @EventListener
    public void handleUpdatedEvent(UpdatedEvent event) {
        int page = 0;
        int size = 1000;
        Page<User> users;

        // Variables specific to this event type
        Map<String, Object> eventVars = Map.of(
                "eventName", event.getEventName()
        );

        do {
           
            users = userRepository.findAll(PageRequest.of(page, size));
            users.getContent().forEach(user -> {
                NotificationRequest req = buildNotificationRequest(
                        user,
                        "EVENT_UPDATED",
                        eventVars
                );
                notificationService.sendNotification(req);
            });
            page++;
        } while (!users.isLast());
    }
}