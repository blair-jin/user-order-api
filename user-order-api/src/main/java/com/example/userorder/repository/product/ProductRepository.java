package com.example.userorder.repository.product;

import com.example.userorder.domain.product.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    @Lock(LockModeType.WRITE)
    @Query("SELECT p FROM Product p WHERE p.id IN :productIds ORDER BY p.id ASC")
    List<Product> findAllByIdWithPessimisticLock(List<Long> productIds);
}