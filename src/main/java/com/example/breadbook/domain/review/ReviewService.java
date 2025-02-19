package com.example.breaadbook.domain.review;

import com.example.breaadbook.domain.review.model.Review;
import com.example.breaadbook.domain.review.model.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Review create(ReviewDto.ReviewDtoReq dto){
        Optional<Member> member = memberRepository.findById(dto.getMemberIdx());
        Optional<Product> product = productRepository.findById(dto.getProductIdx());
        if(member.isEmpty() && product.isEmpty()){
            return reviewRepository.save(Review.builder()
                    .product(product.get())
                    .member(member.get())
                    .rating(dto.getRating())
                    .reviewText(dto.getReviewText())
                    .createdAt(LocalDateTime.now())
                    .build());
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Review> findOrders(Long memberIdx) {
        return reviewRepository.findByMember(memberIdx).orElse(null);
    }

    @Transactional(readOnly = true)
    public Review findOderDetails(Long reviewIdx) {
        return reviewRepository.findById(reviewIdx).orElse(null);
    }
}
