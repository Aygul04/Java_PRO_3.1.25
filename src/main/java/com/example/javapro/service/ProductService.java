package com.example.javapro.service;

import com.example.javapro.model.Product;
import com.example.javapro.model.ProductDto;
import com.example.javapro.model.ProductResponseDto;
import com.example.javapro.model.User;
import com.example.javapro.repo.ProductRepository;
import com.example.javapro.repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepo userRepository;

    public ProductService(ProductRepository productRepository, UserRepo userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ProductResponseDto createProduct(ProductDto productDto) {
        User user = userRepository.findById(productDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Product product = new Product();
        product.setAccountNumber(productDto.getAccountNumber());
        product.setBalance(productDto.getBalance());
        product.setProductType(productDto.getProductType());
        product.setUser(user);

        Product savedProduct = productRepository.save(product);

        return toDto(savedProduct);
    }

    public List<ProductResponseDto> getUserProducts(Long userId) {
        return productRepository.findByUserId(userId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ProductResponseDto getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return toDto(product);
    }

    private ProductResponseDto toDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getAccountNumber(),
                product.getBalance(),
                product.getProductType(),
                product.getUser().getId()
        );
    }
}