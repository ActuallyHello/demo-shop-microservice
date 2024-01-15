package com.happyfxmas.warehousemicroservice.exception.service;

public class SupplierDeleteException extends RuntimeException {
    public SupplierDeleteException(String message) {
        super(message);
    }

    public SupplierDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
