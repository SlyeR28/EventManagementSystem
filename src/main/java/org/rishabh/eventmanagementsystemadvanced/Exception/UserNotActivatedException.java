package org.rishabh.eventmanagementsystemadvanced.Exception;

public class UserNotActivatedException extends EventException{

    public UserNotActivatedException() {
    }

    public UserNotActivatedException(Throwable cause) {
        super(cause);
    }

    public UserNotActivatedException(String message){
        super(message);
    }

    public UserNotActivatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotActivatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
