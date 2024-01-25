package com.happyfxmas.warehousemicroservice.exception.service;

public class InventoryDoesNotExistException extends RuntimeException {
    public InventoryDoesNotExistException(String message) {
        super(message);
    }

    public InventoryDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
