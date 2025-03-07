package com.example.breadbook.domain.wish;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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

    @Operation(summary = "찜하기/취소하기", description = "상품을 찜하거나, 찜한 상품을 해제할 수 있는 기능이다.")
    @PostMapping("/toggle/{productId}")
    public ResponseEntity<Void> toggleWish(@PathVariable Long productId, @AuthenticationPrincipal Member member) {
        wishService.toggleWish(productId, member);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "찜 목록 열람", description = "현재 사용자가 찜한 상품들의 목록을 보여주는 기능이다.")
    @GetMapping("/list")
    public ResponseEntity<List<Product>> getWishList(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(wishService.getWishList(member));
    }
}