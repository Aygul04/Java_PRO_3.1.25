package com.example.javapro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private ProductType productType;
    private Long userId;
}
