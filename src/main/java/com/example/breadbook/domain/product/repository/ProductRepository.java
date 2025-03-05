package com.example.breadbook.domain.product.repository;

import com.example.breadbook.domain.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public Product findByIdx(Long idx);
}
