package com.example.breadbook.domain.review;

import com.example.breadbook.domain.member.repository.MemberRepository;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.order.OrderRepository;
import com.example.breadbook.domain.order.model.Order;
import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.domain.product.repository.ProductRepository;
import com.example.breadbook.domain.review.model.Review;
import com.example.breadbook.domain.review.model.ReviewDto;
import com.example.breadbook.global.response.BaseResponse;
import com.example.breadbook.global.response.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public BaseResponse<Review> regist(ReviewDto.ReviewDtoReq dto) {
        Order order = orderRepository.findByMemberAndProduct(dto.getOrderIdx());

        Review review = Review.builder()
                .product(order.getProduct())
                .member(order.getMember())
                .rating(dto.getRating())
                .order(order)
                .reviewText(dto.getReviewText())
                .createdAt(LocalDateTime.now())
                .build();

        Member member = order.getMember();

        System.out.println("Member ID: " + member.getIdx());

        // 점수 업데이트 (기존 점수가 null이면 0으로 설정 후 더하기)
        member.setScore((member.getScore() == null ? 0 : member.getScore()) + dto.getRating());


        updateMemberScore(member); // 별도 트랜잭션으로 점수 업데이트
        reviewRepository.save(review);
        return new BaseResponse<>(BaseResponseMessage.REVIEW_REGISTER_SUCCESS, review);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateMemberScore(Member member) {
        memberRepository.save(member);
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
