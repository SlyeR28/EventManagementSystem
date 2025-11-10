package org.rishabh.eventmanagementsystemadvanced.Utils.EventListeners.CartLifeCycle;

import org.springframework.context.ApplicationEvent;

public class CartNoti  extends ApplicationEvent {

    public CartNoti(Object source) {
        super(source);
    }
}
