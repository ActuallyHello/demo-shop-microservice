package com.happyfxmas.ordermicroservice.exception;

import com.happyfxmas.ordermicroservice.exception.communication.ProductsDoesNotExistException;
import com.happyfxmas.ordermicroservice.exception.service.ItemCreationException;
import com.happyfxmas.ordermicroservice.exception.service.ItemDeleteException;
import com.happyfxmas.ordermicroservice.exception.service.ItemDoesNotExistException;
import com.happyfxmas.ordermicroservice.exception.service.OrderCreationException;
import com.happyfxmas.ordermicroservice.exception.service.OrderDeleteException;
import com.happyfxmas.ordermicroservice.exception.service.OrderDoesNotExistException;
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

@RestControllerAdvice(basePackages = "com.happyfxmas.ordermicroservice")
@Slf4j
public class OrderExceptionHandler {

    @ExceptionHandler(value = {
            ItemDoesNotExistException.class,
            OrderDoesNotExistException.class,
    })
    public ResponseEntity<ExceptionDTO> handleNotFoundException(RuntimeException notFoundException) {
        log.error("NOT FOUND EXCEPTION OCCURRED!");
        var exceptionDTO = ExceptionDTO.of(
                notFoundException.getMessage(),
                notFoundException.getClass().getSimpleName(),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {
            ItemCreationException.class,
            ItemDeleteException.class,
            OrderCreationException.class,
            OrderDeleteException.class
    })
    public ResponseEntity<ExceptionDTO> handleServerException(RuntimeException serverException) {
        log.error("DATABASE EXCEPTION OCCURRED!");
        var exceptionDTO = ExceptionDTO.of(
                serverException.getMessage(),
                serverException.getClass().getSimpleName(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class,})
    public ResponseEntity<ExceptionDTO> handleIllegalArgumentException(RuntimeException exception) {
        log.error("VALIDATION EXCEPTION OCCURRED!");
        var exceptionDTO = ExceptionDTO.of(
                exception.getMessage(),
                exception.getClass().getSimpleName(),
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
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

    @ExceptionHandler(value = ProductsDoesNotExistException.class)
    public ResponseEntity<Object> handleValidationException(ProductsDoesNotExistException productsDoesNotExistException) {
        var errorMap =
                Map.of("Products", productsDoesNotExistException.getUnavailableProductIds());
        var exceptionDTO = ExceptionDTO.of(
                errorMap,
                productsDoesNotExistException.getClass().getSimpleName(),
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
