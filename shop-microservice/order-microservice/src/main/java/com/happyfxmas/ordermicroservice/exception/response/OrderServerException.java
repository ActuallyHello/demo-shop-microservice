package com.happyfxmas.ordermicroservice.exception.response;

public class OrderServerException extends RuntimeException {
    public OrderServerException(String message) {
        super(message);
    }

    public OrderServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
