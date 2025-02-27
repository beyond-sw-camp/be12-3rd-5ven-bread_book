package com.example.breadbook.domain.review;

import com.example.breadbook.domain.member.MemberRepository;
import com.example.breadbook.domain.member.model.Member;

import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.domain.product.repository.ProductRepository;
import com.example.breadbook.domain.review.model.Review;
import com.example.breadbook.domain.review.model.ReviewDto;
import com.example.breadbook.global.response.BaseResponse;
import com.example.breadbook.global.response.BaseResponseMessage;
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
    public BaseResponse<Review> regist(ReviewDto.ReviewDtoReq dto){
        Optional<Member> member = memberRepository.findById(dto.getMemberIdx());
        Optional<Product> product = productRepository.findById(dto.getProductIdx());
        if(member.isEmpty() && product.isEmpty()){
            Review review =  Review.builder()
                    .product(product.get())
                    .member(member.get())
                    .rating(dto.getRating())
                    .reviewText(dto.getReviewText())
                    .createdAt(LocalDateTime.now())
                    .build();
            reviewRepository.save(review);
            return new BaseResponse(BaseResponseMessage.REVIEW_REGISTER_SUCCESS,review);
        }
        return new BaseResponse(BaseResponseMessage.INTERNAL_SERVER_ERROR);
    }

    @Transactional(readOnly = true)
    public BaseResponse<Review> findReview(Long productIdx) {
        Optional<Product> product = productRepository.findById(productIdx);
        Optional<Review> result = reviewRepository.findByProduct(product.get());
        if(result.isPresent()){
            return new BaseResponse(BaseResponseMessage.REVIEW_FIND_SUCCESS,result.get());
        }
        return new BaseResponse(BaseResponseMessage.INTERNAL_SERVER_ERROR);
    }

    @Transactional(readOnly = true)
    public Review findOderDetails(Long reviewIdx) {
        return reviewRepository.findById(reviewIdx).orElse(null);
    }
}
