package com.happyfxmas.ordermicroservice.exception.service;

public class ItemStatusCreationException extends RuntimeException {
    public ItemStatusCreationException(String message) {
        super(message);
    }

    public ItemStatusCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
