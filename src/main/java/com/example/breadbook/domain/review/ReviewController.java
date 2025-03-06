package com.example.breadbook.domain.review;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.ProductDto;
import com.example.breadbook.domain.review.model.Review;
import com.example.breadbook.domain.review.model.ReviewDto;
import com.example.breadbook.global.response.BaseResponse;
import com.example.breadbook.global.response.BaseResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Tag(name = "리뷰 컨트롤러")
public class ReviewController {
    private final ReviewService reviewService;
    @PostMapping("/regist")
    @Operation(summary = "리뷰 작성", description = "리뷰를 작성하는 기능입니다.")
    public ResponseEntity<BaseResponse<Long>> registerReview(@RequestBody ReviewDto.ReviewDtoReq dto) {
        return ResponseEntity.ok(reviewService.regist(dto));
    }

    @GetMapping("/find/{idx}")
    @Operation(summary = "리뷰 목록", description = "자신의 상품의 리뷰를 확인하는 기능입니다.")
    public ResponseEntity<BaseResponse<List<Review>>> reviewFind(@PathVariable Long memberIdx) {
        return ResponseEntity.ok(reviewService.findReview(memberIdx));
    }

    @DeleteMapping("/delete/{reviewIdx}")
    public ResponseEntity<BaseResponse<Long>> deleteItem(@PathVariable Long reviewIdx) throws Exception {

        return ResponseEntity.ok(reviewService.deleteReview(reviewIdx));
    }
}
