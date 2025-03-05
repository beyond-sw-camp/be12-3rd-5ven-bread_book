package com.example.breadbook.domain.wish;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.domain.product.repository.ProductRepository;
import com.example.breadbook.domain.wish.model.Wish;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishService {
    private final WishRepository wishRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void toggleWish(Long productIdx, Member member) {
        Product product = productRepository.findById(productIdx)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        Wish wish = wishRepository.findByMemberAndProduct(member, product)
                .orElse(Wish.builder()
                        .member(member)
                        .product(product)
                        .canceled(true)  // 기본적으로 취소 상태로 생성
                        .build());

        wish.setCanceled(!wish.isCanceled());
        wishRepository.save(wish);
    }

    @Transactional
    public List<Product> getWishList(Member member) {
        return wishRepository.findAllByMemberAndCanceledFalse(member)
                .stream().map(Wish::getProduct).toList();
    }
}
