package com.example.paymetscore.service;

import com.example.javapro.model.ProductDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductClient {

    private final RestTemplate paymentClient;
    @Value("${product.service.url}")
    private String productServiceUrl;

    public ProductClient(RestTemplate paymentClient) {
        this.paymentClient = paymentClient;
    }

    public ProductDto getProduct(Long productId) {
        String url = productServiceUrl + "/" + productId;
        return paymentClient.getForObject(url, ProductDto.class);

    }

}