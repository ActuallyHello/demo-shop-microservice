package com.happyfxmas.ordermicroservice.exception.service;

public class OrderDoesNotExistException extends RuntimeException {
    public OrderDoesNotExistException(String message) {
        super(message);
    }

    public OrderDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
