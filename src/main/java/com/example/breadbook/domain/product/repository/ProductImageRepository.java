package com.example.breadbook.domain.product.repository;

import com.example.breadbook.domain.product.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    public List<ProductImage> findByProductIdx(Long productIdx);
}
