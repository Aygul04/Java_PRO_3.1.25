package com.example.javapro.service;

import com.example.javapro.model.Product;
import com.example.javapro.model.ProductDto;
import com.example.javapro.model.User;
import com.example.javapro.repo.ProductRepository;
import com.example.javapro.repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepo userRepository;

    public ProductService(ProductRepository productRepository, UserRepo userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Product createProduct(ProductDto productDto) {
        User user = userRepository.findById(productDto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Product product = new Product();
        product.setAccountNumber(productDto.getAccountNumber());
        product.setBalance(productDto.getBalance());
        product.setProductType(productDto.getProductType());
        product.setUser(user);

        return productRepository.save(product);
    }

    public List<Product> getUserProducts(Long userId) {
        return productRepository.findByUserId(userId);
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }
}