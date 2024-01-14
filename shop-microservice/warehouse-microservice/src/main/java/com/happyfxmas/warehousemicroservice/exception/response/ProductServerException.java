package com.happyfxmas.warehousemicroservice.exception.response;

import com.happyfxmas.warehousemicroservice.exception.ServerException;

public class ProductServerException extends RuntimeException implements ServerException {
    public ProductServerException(String message) {
        super(message);
    }

    public ProductServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
