package org.rishabh.eventmanagementsystemadvanced.Exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message , Throwable cause){
        super(message,cause);
    }
}
