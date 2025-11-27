package org.rishabh.eventmanagementsystemadvanced.Exception;

public class EventException extends RuntimeException {

    public EventException() {
    }

    public EventException(String message) {
        super(message);
    }

    public EventException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public EventException(Throwable cause) {
        super(cause);
    }
}
