package org.rishabh.eventmanagementsystemadvanced.Exception;

public class OrderNotFound extends EventException {

    public OrderNotFound() {
    }

    public OrderNotFound(String message) {
        super(message);
    }

    public OrderNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public OrderNotFound(Throwable cause) {
        super(cause);
    }
}
