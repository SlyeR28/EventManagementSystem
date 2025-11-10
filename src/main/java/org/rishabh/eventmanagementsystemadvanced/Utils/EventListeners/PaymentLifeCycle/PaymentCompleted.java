package org.rishabh.eventmanagementsystemadvanced.Utils.EventListeners.PaymentLifeCycle;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PaymentCompleted extends ApplicationEvent {

    private final Long userId;
    private final Double amount;
    private final Long orderId;


    public PaymentCompleted(Object source, Double amount, Long userId, Long orderId) {
        super(source);
        this.amount = amount;
        this.userId = userId;
        this.orderId = orderId;

    }
}
