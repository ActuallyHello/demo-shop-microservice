package com.happyfxmas.warehousemicroservice.exception.service;

public class InventoryDeleteException extends RuntimeException{
    public InventoryDeleteException(String message) {
        super(message);
    }

    public InventoryDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
