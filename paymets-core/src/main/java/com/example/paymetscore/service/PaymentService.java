package com.example.paymetscore.service;

import com.example.javapro.model.ProductDto;
import com.example.paymetscore.dto.PaymentRequest;
import com.example.paymetscore.dto.PaymentResponse;
import com.example.paymetscore.exception.InsufficientFundsException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    private final ProductClient productClient;

    public PaymentService(ProductClient productClient) {
        this.productClient = productClient;
    }

    public PaymentResponse executePayment(PaymentRequest request) {
        ProductDto product = productClient.getProduct(request.getFromProductId());

        if (product.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientFundsException("Недостаточно средств");
        }

        return new PaymentResponse("SUCCESS", "Платеж выполнен", UUID.randomUUID().toString());
    }

    public ProductDto getProduct(Long id) {
        return productClient.getProduct(id);
    }
}