package com.example.breadbook.domain.wish;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.domain.product.model.ProductDto;
//import com.example.breadbook.domain.wish.model.WishDto;
import com.example.breadbook.global.response.BaseResponse;
import com.example.breadbook.global.response.BaseResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "상품 찜하기 기능")
@RestController
@RequestMapping("/wish")
@RequiredArgsConstructor
public class WishController {
    private final WishService wishService;

    @PostMapping("/toggle/{productIdx}")
    public ResponseEntity<Void> toggleWish(@PathVariable Long productIdx, @AuthenticationPrincipal Member member) {
        wishService.toggleWish(productIdx, member);
        return ResponseEntity.ok().build();
    }

//    @Operation(summary = "찜 목록 열람", description = "현재 사용자가 찜한 상품들의 목록을 보여주는 기능이다.")
//    @GetMapping("/list")
//    public ResponseEntity<BaseResponse<Page<ProductDto.ListResponse>>> getWishList(
//            @AuthenticationPrincipal Member member,
//            @PageableDefault(size = 16, sort = "price", direction = Sort.Direction.DESC) Pageable pageable){
//        Page<ProductDto.ListResponse> response = wishService(getWishList(member, pageable));
//        return ResponseEntity.ok(wishService.getWishList(member));
//    }

    @Operation(summary = "찜 목록 열람", description = "현재 사용자가 찜한 상품들의 목록을 보여주는 기능이다.")
    @GetMapping("/list")
    public ResponseEntity<List<Product>> getWishList(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(wishService.getWishList(member));
    }
}