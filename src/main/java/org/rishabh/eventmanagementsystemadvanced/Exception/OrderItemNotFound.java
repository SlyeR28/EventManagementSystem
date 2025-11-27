package org.rishabh.eventmanagementsystemadvanced.Exception;

public class OrderItemNotFound extends EventException {
    public OrderItemNotFound(String message) {
        super(message);
    }

    public OrderItemNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderItemNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public OrderItemNotFound() {
    }

    public OrderItemNotFound(Throwable cause) {
        super(cause);
    }
}
