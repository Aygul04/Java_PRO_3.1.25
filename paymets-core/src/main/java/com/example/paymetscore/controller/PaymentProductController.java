package com.example.paymetscore.controller;

import com.example.javapro.model.ProductDto;
import com.example.paymetscore.dto.PaymentRequest;
import com.example.paymetscore.dto.PaymentResponse;
import com.example.paymetscore.service.PaymentService;
import com.example.paymetscore.service.ProductClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments/products")
public class PaymentProductController {

    private final ProductClient productClient;
    private final PaymentService paymentService;

    public PaymentProductController(ProductClient productClient, PaymentService paymentService) {
        this.productClient = productClient;
        this.paymentService = paymentService;
    }

    @GetMapping("/{productId}")
    public ProductDto getProduct(@PathVariable Long productId) {
        return productClient.getProduct(productId);
    }

    @PostMapping("/execute")
    public PaymentResponse executePayment(@RequestBody PaymentRequest request) {
        return paymentService.executePayment(request);
    }
}