package com.happyfxmas.warehousemicroservice.exception.service;

public class ProductDoesNotExistException extends RuntimeException {
    public ProductDoesNotExistException(String message) {
        super(message);
    }

    public ProductDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
