package org.rishabh.eventmanagementsystemadvanced.Exception;


public class EmailException extends EventException{
    public EmailException(String message){
        super(message);
    }

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public EmailException() {
    }

    public EmailException(Throwable cause) {
        super(cause);
    }
}
