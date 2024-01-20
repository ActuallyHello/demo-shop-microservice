package com.happyfxmas.ordermicroservice.exception.service;

public class ItemDeleteException extends RuntimeException {

    public ItemDeleteException(String message) {
        super(message);
    }

    public ItemDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
