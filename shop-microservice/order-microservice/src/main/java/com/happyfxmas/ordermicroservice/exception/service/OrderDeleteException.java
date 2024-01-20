package com.happyfxmas.ordermicroservice.exception.service;

public class OrderDeleteException extends RuntimeException {

    public OrderDeleteException(String message) {
        super(message);
    }

    public OrderDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
