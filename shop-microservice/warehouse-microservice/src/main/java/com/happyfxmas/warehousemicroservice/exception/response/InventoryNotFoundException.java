package com.happyfxmas.warehousemicroservice.exception.response;

import com.happyfxmas.warehousemicroservice.exception.NotFoundException;

public class InventoryNotFoundException extends RuntimeException implements NotFoundException {
    public InventoryNotFoundException(String message) {
        super(message);
    }

    public InventoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
