package com.example.javapro.service;

import com.example.javapro.mapper.ProductMapper;
import com.example.javapro.model.Product;
import com.example.javapro.model.ProductDto;
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
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, UserRepo userRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        User user = userRepository.findById(productDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Product product = productMapper.toEntity(productDto);
        product.setAccountNumber(productDto.getAccountNumber());
        product.setBalance(productDto.getBalance());
        product.setProductType(productDto.getProductType());
        product.setUser(user);

        Product savedProduct = productRepository.save(product);

        return productMapper.toDto(savedProduct);
    }

    public List<ProductDto> getUserProducts(Long userId) {
        return productRepository.findByUserId(userId).stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProduct(Long productId) {
        return productRepository.findById(productId).map(productMapper::toDto).orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

}