package com.happyfxmas.ordermicroservice.exception.response;

public class ItemValidationException extends RuntimeException {
    public ItemValidationException(String message) {
        super(message);
    }

    public ItemValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
