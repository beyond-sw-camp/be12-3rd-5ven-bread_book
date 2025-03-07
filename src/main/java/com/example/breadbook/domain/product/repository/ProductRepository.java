package com.example.breadbook.domain.product.repository;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    public Product findByIdx(Long idx);
    public Boolean existsByIdx(Long idx);
    @Query("SELECT p FROM Product p " +
            "LEFT JOIN FETCH p.book b " +
            "LEFT JOIN FETCH p.orders o " +
            "LEFT JOIN FETCH o.review r " +
            "LEFT JOIN FETCH p.category c " +
            "LEFT JOIN FETCH p.member m " +
            "LEFT JOIN FETCH o.member m2 " +
            "WHERE p.member.idx = :memberIdx")
    Page<Product> findByProductWithMemberOrder(@Param("memberIdx") Long memberIdx, Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "LEFT JOIN FETCH p.book b " +
            "LEFT JOIN FETCH p.orders o " +
            "LEFT JOIN FETCH o.review r " +
            "LEFT JOIN FETCH p.category c " +
            "LEFT JOIN FETCH p.member m " +
            "LEFT JOIN FETCH o.member m2 " +
            "WHERE p.member.idx = :memberIdx")
    Page<Product> findByProductWithMemberPay(@Param("memberIdx") Long memberIdx, Pageable pageable);

}


