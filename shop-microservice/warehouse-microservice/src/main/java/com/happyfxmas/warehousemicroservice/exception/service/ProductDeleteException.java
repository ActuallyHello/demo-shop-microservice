package com.happyfxmas.warehousemicroservice.exception.service;

public class ProductDeleteException extends RuntimeException{
    public ProductDeleteException(String message) {
        super(message);
    }

    public ProductDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
