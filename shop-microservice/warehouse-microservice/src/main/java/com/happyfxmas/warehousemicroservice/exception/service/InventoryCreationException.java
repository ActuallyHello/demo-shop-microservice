package com.happyfxmas.warehousemicroservice.exception.service;

public class InventoryCreationException extends RuntimeException {
    public InventoryCreationException(String message) {
        super(message);
    }

    public InventoryCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
