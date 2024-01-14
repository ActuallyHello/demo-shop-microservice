package com.happyfxmas.warehousemicroservice.exception;

import org.springframework.http.HttpStatus;

public record ExceptionDTO(Object message, String causeClass, HttpStatus httpStatus, int httpStatusCode) {
    public static ExceptionDTO of(Object message, String causeClass, HttpStatus httpStatus, int httpStatusCode) {
        return new ExceptionDTO(message, causeClass, httpStatus, httpStatusCode);
    }
}
