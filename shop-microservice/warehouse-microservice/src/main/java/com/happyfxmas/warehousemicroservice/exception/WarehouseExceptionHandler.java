package com.happyfxmas.warehousemicroservice.exception;

import com.happyfxmas.warehousemicroservice.exception.service.InventoryCreationException;
import com.happyfxmas.warehousemicroservice.exception.service.InventoryDeleteException;
import com.happyfxmas.warehousemicroservice.exception.service.InventoryDoesNotExistException;
import com.happyfxmas.warehousemicroservice.exception.service.ProductCreationException;
import com.happyfxmas.warehousemicroservice.exception.service.ProductDeleteException;
import com.happyfxmas.warehousemicroservice.exception.service.ProductDoesNotExistException;
import com.happyfxmas.warehousemicroservice.exception.service.SupplierCreationException;
import com.happyfxmas.warehousemicroservice.exception.service.SupplierDeleteException;
import com.happyfxmas.warehousemicroservice.exception.service.SupplierDoesNotExistException;
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
            ProductDoesNotExistException.class,
            SupplierDoesNotExistException.class,
            InventoryDoesNotExistException.class
    })
    public ResponseEntity<ExceptionDTO> handleNotFoundException(RuntimeException notFoundException) {
        var exceptionDTO = ExceptionDTO.of(
                notFoundException.getMessage(),
                notFoundException.getClass().getSimpleName(),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {
            ProductCreationException.class,
            ProductDeleteException.class,
            SupplierCreationException.class,
            SupplierDeleteException.class,
            InventoryCreationException.class,
            InventoryDeleteException.class,
    })
    public ResponseEntity<ExceptionDTO> handleServerException(RuntimeException serverException) {
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

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<ExceptionDTO> handleIllegalArgumentException(RuntimeException exception) {
        log.error("VALIDATION EXCEPTION OCCURRED!");
        var exceptionDTO = ExceptionDTO.of(
                exception.getMessage(),
                exception.getClass().getSimpleName(),
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
