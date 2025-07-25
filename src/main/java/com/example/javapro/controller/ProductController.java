package com.example.javapro.controller;

import com.example.javapro.model.ProductDto;
import com.example.javapro.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")

public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @GetMapping("/user/{userId}")
    public List<ProductDto> getUserProducts(@PathVariable Long userId) {
        return productService.getUserProducts(userId);
    }

    @GetMapping("/{productId}")
    public ProductDto getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }
}