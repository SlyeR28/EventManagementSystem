package org.rishabh.eventmanagementsystemadvanced.Exception;

public class TicketTypeException extends EventException {

    public TicketTypeException(String message) {
        super(message);
    }

    public TicketTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public TicketTypeException() {
    }

    public TicketTypeException(Throwable cause) {
        super(cause);
    }
}
