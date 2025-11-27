package org.rishabh.eventmanagementsystemadvanced.Exception;

public class ImageException extends EventException{
    public ImageException(String message){
        super(message);
    }

    public ImageException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ImageException() {
    }

    public ImageException(Throwable cause) {
        super(cause);
    }
}
