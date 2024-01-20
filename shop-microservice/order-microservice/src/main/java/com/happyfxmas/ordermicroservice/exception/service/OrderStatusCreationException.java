package com.happyfxmas.ordermicroservice.exception.service;

public class OrderStatusCreationException extends RuntimeException {
    public OrderStatusCreationException(String message) {
        super(message);
    }

    public OrderStatusCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
