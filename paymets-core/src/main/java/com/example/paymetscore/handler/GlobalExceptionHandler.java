package com.example.paymetscore.handler;

import com.example.paymetscore.dto.ErrorResponse;
import com.example.paymetscore.exception.InsufficientFundsException;
import com.example.paymetscore.exception.ProductServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductServiceException.class)
    public ErrorResponse handleProductServiceError(ProductServiceException ex) {
        return new ErrorResponse("PRODUCT_SERVICE_ERROR",
                "Ошибка при обращении к сервису продуктов: " + ex.getMessage());
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ErrorResponse handleFundsError(InsufficientFundsException ex) {
        return new ErrorResponse("INSUFFICIENT_FUNDS", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleOtherErrors(Exception ex) {
        return new ErrorResponse("INTERNAL_ERROR", "Внутренняя ошибка");
    }
}