package com.happyfxmas.ordermicroservice.exception.service;

public class ItemStatusDeleteException extends RuntimeException {

    public ItemStatusDeleteException(String message) {
        super(message);
    }

    public ItemStatusDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
