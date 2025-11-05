package org.rishabh.eventmanagementsystemadvanced.Exception;

public class CategoryNotFoundException extends EventTicketException{

    public CategoryNotFoundException() {

    }

    public CategoryNotFoundException(Throwable cause) {
        super(cause);
    }

    public CategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    protected CategoryNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
