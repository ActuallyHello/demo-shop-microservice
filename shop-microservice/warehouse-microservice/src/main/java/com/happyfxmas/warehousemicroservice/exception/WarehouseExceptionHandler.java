package com.happyfxmas.warehousemicroservice.exception;

import com.happyfxmas.warehousemicroservice.exception.response.InventoryNotFoundException;
import com.happyfxmas.warehousemicroservice.exception.response.InventoryServerException;
import com.happyfxmas.warehousemicroservice.exception.response.ProductNotFoundException;
import com.happyfxmas.warehousemicroservice.exception.response.ProductServerException;
import com.happyfxmas.warehousemicroservice.exception.response.SupplierNotFoundException;
import com.happyfxmas.warehousemicroservice.exception.response.SupplierServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "com.happyfxmas.warehousemicroservice")
@Slf4j
public class WarehouseExceptionHandler {

    @ExceptionHandler(value = {
            ProductNotFoundException.class,
            SupplierNotFoundException.class,
            InventoryNotFoundException.class
    })
    public ResponseEntity<ExceptionDTO> handleNotFoundException(NotFoundException notFoundException) {
        var exceptionDTO = ExceptionDTO.of(
                notFoundException.getMessage(),
                notFoundException.getClass().getSimpleName(),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {
            ProductServerException.class,
            SupplierServerException.class,
            InventoryServerException.class
    })
    public ResponseEntity<ExceptionDTO> handleServerException(ServerException serverException) {
        var exceptionDTO = ExceptionDTO.of(
                serverException.getMessage(),
                serverException.getClass().getSimpleName(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, List<String>> errorMap = methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                FieldError::getField,
                                Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                        )
                );
        var exceptionDTO = ExceptionDTO.of(
                errorMap,
                methodArgumentNotValidException.getClass().getSimpleName(),
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ExceptionDTO> handleOtherException(Exception exception) {
        log.error("UNEXPECTED EXCEPTION OCCURRED! {}", exception.getMessage());
        var exceptionDTO = ExceptionDTO.of(
                exception.getMessage(),
                exception.getClass().getSimpleName(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
