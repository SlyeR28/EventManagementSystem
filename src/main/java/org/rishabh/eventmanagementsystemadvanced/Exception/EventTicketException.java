package org.rishabh.eventmanagementsystemadvanced.Exception;

public class EventTicketException extends EventException{

    public EventTicketException() {

    }

    public EventTicketException(Throwable cause) {
        super(cause);
    }

    public EventTicketException(String message) {
        super(message);
    }

    public EventTicketException(String message, Throwable cause) {
        super(message, cause);
    }

    protected EventTicketException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
