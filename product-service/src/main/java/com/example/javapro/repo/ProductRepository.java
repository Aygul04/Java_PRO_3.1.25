package com.example.javapro.repo;

import com.example.javapro.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByUserId(Long userId);

    @Query("SELECT p FROM Product p WHERE p.accountNumber = :accountNumber")
    Optional<Product> findByAccountNumber(@Param("accountNumber") String accountNumber);

}
