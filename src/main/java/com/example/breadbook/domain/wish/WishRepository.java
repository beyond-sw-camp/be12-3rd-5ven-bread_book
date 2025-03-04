package com.example.breadbook.domain.wish;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.domain.wish.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {
    Optional<Wish> findByMemberAndProduct(Member member, Product product);
    List<Wish> findAllByMemberAndCanceledFalse(Member member);

    List<Wish> findByMemberIdx(Long idx);
}