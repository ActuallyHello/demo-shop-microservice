package com.happyfxmas.warehousemicroservice.exception.service;

public class SupplierCreationException extends RuntimeException {
    public SupplierCreationException(String message) {
        super(message);
    }

    public SupplierCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
