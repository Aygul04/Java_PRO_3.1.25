package com.example.paymetscore.handler;

import com.example.paymetscore.exception.InsufficientFundsException;
import com.example.paymetscore.exception.ProductServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<String> handleProductServiceError(ProductServiceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body("Ошибка при обращении к сервису продуктов: " + ex.getMessage());
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<String> handleFundsError(InsufficientFundsException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherErrors(Exception ex) {
        return ResponseEntity.internalServerError().body("Внутренняя ошибка");
    }
}