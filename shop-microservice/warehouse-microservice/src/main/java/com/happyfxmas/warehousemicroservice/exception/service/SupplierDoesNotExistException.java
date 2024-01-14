package com.happyfxmas.warehousemicroservice.exception.service;

public class SupplierDoesNotExistException extends RuntimeException {
    public SupplierDoesNotExistException(String message) {
        super(message);
    }

    public SupplierDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
