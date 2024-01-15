package com.happyfxmas.warehousemicroservice.exception.response;

import com.happyfxmas.warehousemicroservice.exception.ServerException;

public class InventoryServerException extends RuntimeException implements ServerException {
    public InventoryServerException(String message) {
        super(message);
    }

    public InventoryServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
