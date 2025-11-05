package org.rishabh.eventmanagementsystemadvanced.Domains.Modal;

import lombok.Getter;

@Getter
public enum NotificationTemplateType {

    EVENT_DRAFT_CREATED("Event Draft Created", "Your event draft '{{eventName}}' has been created successfully."),
    EVENT_PUBLISHED("Event Published", "Your event '{{eventName}}' is now live! Ticket sales have begun."),
    EVENT_UPDATED("Event Updated", "Your event '{{eventName}}' has been updated successfully."),
    EVENT_CANCELLED("Event Cancelled", "Your event '{{eventName}}' has been cancelled."),

    TICKET_SALES_STARTED("Ticket Sales Started", "Tickets for '{{eventName}}' are now available at ₹{{ticketPrice}}."),
    TICKET_PURCHASED("Ticket Purchased", "Thanks for purchasing tickets for '{{eventName}}'. Your QR code has been emailed."),
    PAYMENT_SUCCESSFUL("Payment Successful", "Your payment of ₹{{amount}} for '{{eventName}}' was successful."),

    DEFAULT("General Notification", "Something happened in your account. Please check your dashboard.");

    private final String title;
    private final String message;

    NotificationTemplateType(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
