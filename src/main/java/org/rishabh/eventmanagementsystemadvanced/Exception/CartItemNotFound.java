package org.rishabh.eventmanagementsystemadvanced.Exception;

public class CartItemNotFound extends EventException {
    public CartItemNotFound(String message) {
        super(message);
    }

    public CartItemNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public CartItemNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CartItemNotFound() {
    }

    public CartItemNotFound(Throwable cause) {
        super(cause);
    }
}
