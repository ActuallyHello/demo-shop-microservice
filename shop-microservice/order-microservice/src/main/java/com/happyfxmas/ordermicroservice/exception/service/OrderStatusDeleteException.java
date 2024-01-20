package com.happyfxmas.ordermicroservice.exception.service;

public class OrderStatusDeleteException extends RuntimeException {
    public OrderStatusDeleteException(String message) {
        super(message);
    }

    public OrderStatusDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
