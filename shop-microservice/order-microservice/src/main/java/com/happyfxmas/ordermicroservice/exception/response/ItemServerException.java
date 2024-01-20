package com.happyfxmas.ordermicroservice.exception.response;

public class ItemServerException extends RuntimeException {
    public ItemServerException(String message) {
        super(message);
    }

    public ItemServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
