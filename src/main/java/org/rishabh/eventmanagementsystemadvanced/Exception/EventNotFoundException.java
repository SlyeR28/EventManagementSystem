package org.rishabh.eventmanagementsystemadvanced.Exception;

public class EventNotFoundException extends EventException {

    public EventNotFoundException() {
    }

    public EventNotFoundException(String message) {
        super(message);
    }

    public EventNotFoundException(Throwable cause) {
        super(cause);
    }

    public EventNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    protected EventNotFoundException(String message, Throwable cause, boolean enableSuppression
            , boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
