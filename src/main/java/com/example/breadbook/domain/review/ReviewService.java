package com.example.breadbook.domain.review;

import com.example.breadbook.domain.member.repository.MemberRepository;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.order.OrderRepository;
import com.example.breadbook.domain.order.model.Order;
import com.example.breadbook.domain.order.model.OrderDto;
import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.domain.product.repository.ProductRepository;
import com.example.breadbook.domain.review.model.Review;
import com.example.breadbook.domain.review.model.ReviewDto;
import com.example.breadbook.global.response.BaseResponse;
import com.example.breadbook.global.response.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public BaseResponse<Long> regist(ReviewDto.ReviewDtoReq dto) {
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
        return new BaseResponse<>(BaseResponseMessage.REVIEW_REGISTER_SUCCESS, review.getProduct().getIdx());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateMemberScore(Member member) {
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public BaseResponse<List<ReviewDto.ReviewDtoResp>> findReview(ReviewDto.ReviewListReq dto) {
        Pageable pageable = PageRequest.of(dto.getPage(), 5);
        Page<Product> products = productRepository.findByProductWithMemberPay(dto.getMemberIdx(),pageable);

        List<ReviewDto.ReviewDtoResp> result = products.getContent().stream().map(ReviewDto.ReviewDtoResp::of).toList();

        if(result.size()>0){
            return new BaseResponse(BaseResponseMessage.REVIEW_FIND_SUCCESS,result);
        }
        return new BaseResponse(BaseResponseMessage.INTERNAL_SERVER_ERROR);
    }


    public BaseResponse<Long> deleteReview(Long reviewIdx) {
        Review review= reviewRepository.findById(reviewIdx).get();
        try{
            reviewRepository.delete(review);
            return new BaseResponse(BaseResponseMessage.REVIEW_DELETE_SUCCESS,review.getIdx());
        }catch (Exception e){
            return new BaseResponse(BaseResponseMessage.INTERNAL_SERVER_ERROR);
        }
    }
}
