package com.example.paymetscore.service;

import com.example.javapro.model.ProductDto;
import com.example.paymetscore.exception.ProductServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductClient {

    private final RestTemplate restTemplate;
    @Value("${product.service.url}")
    private String productServiceUrl;

    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProductDto getProduct(Long productId) {
        String url = productServiceUrl + "/" + productId;
        try {
            return restTemplate.getForObject(url, ProductDto.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw new ProductServiceException("Failed to get product", ex);
        }
    }

}