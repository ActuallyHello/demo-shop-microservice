package com.happyfxmas.warehousemicroservice.exception.response;

import com.happyfxmas.warehousemicroservice.exception.ServerException;

public class SupplierServerException extends RuntimeException implements ServerException {
    public SupplierServerException(String message) {
        super(message);
    }

    public SupplierServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
