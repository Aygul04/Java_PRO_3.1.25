package com.example.javapro.model;

import java.math.BigDecimal;


public class ProductDto {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private ProductType productType;
    private Long userId;

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", productType=" + productType +
                ", userId=" + userId +
                '}';
    }

    public ProductDto(Long id, String accountNumber, BigDecimal balance, ProductType productType, Long userId) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.productType = productType;
        this.userId = userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Long getUserId() {
        return userId;
    }
}