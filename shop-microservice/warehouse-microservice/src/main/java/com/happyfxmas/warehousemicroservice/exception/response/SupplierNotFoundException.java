package com.happyfxmas.warehousemicroservice.exception.response;

import com.happyfxmas.warehousemicroservice.exception.NotFoundException;

public class SupplierNotFoundException extends RuntimeException implements NotFoundException {
    public SupplierNotFoundException(String message) {
        super(message);
    }

    public SupplierNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
