package com.example.breadbook.domain.product.model;

import com.example.breadbook.domain.book.model.Book;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.BookCondition;
import com.example.breadbook.domain.product.ProductStatus;
import com.example.breadbook.domain.product.ScoreCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductDto {
    @Getter
    @Schema(description = "중고책 상품 등록/수정 요청 DTO")
    public static class RegisterRequest {
        // private Book book; //frontend 사용자 선택 => 어떻게 바꾸지???
        // private Category category; // frontend 사용자 선택 => json 스트링값
        @Schema(description = "책의 고유 식별값", example = "1")
        private Long bookIdx;
        @Schema(description = "책의 분류", example = "한국소설")
        private String categoryName;
        @Schema(description = "판매가", example = "5000")
        private Long price;
        @Schema(description = "책의 보존 상태", example = "GOOD")
        private BookCondition bookCondition;
        @Schema(description = "중고 거래 선호 방식", example = "직거래 선호, 반값택배 가능")
        private String tradeMethod;
        @Schema(description = "직거래 시에 거래 희망장소", example = "신대방삼거리역 1번 출구")
        private String tradeLocation;
        @Schema(description = "상품 설명", example = "뒷면에 미세한 찍힘이 있지만 (사진 첨부), 꺠끗하게 읽었습니다.")
        private String description;

        public Product toEntity(Member member, Book book, Category category) {
            return Product.builder()
                    .member(member)
                    .book(book)
                    .category(category)
                    .price(price)
                    .bookCondition(bookCondition)
                    .tradeMethod(tradeMethod)
                    .tradeLocation(tradeLocation)
                    .description(description)
                    .createdAt(LocalDateTime.now())
                    .build();
        }
    }

    @Getter @NoArgsConstructor @AllArgsConstructor @Builder
    @Schema(description = "중고책 상품 등록/수정 응답 DTO")
    public static class ProductResponse {
        @Schema(description = "상품의 고유 식별 값", example="1")
        private Long idx;
        @Schema(description = "판매자 빵등급(신뢰등급)", example="식빵")
        private String scoreCategory;
        @Schema(description ="판매자 닉네임", example = "정오")
        private String sellerName;
        @Schema(description = "등록된 책 정보")
        private Book book;
        @Schema(description = "판매자가 설정한 책분류 정보")
        private Category category;
        @Schema(description = "판매가", example = "5000")
        private Long price;
        @Schema(description = "책의 보존 상태", example = "GOOD")
        private BookCondition bookCondition;
        @Schema(description = "중고 거래 선호 방식", example = "직거래 선호, 반값택배 가능")
        private String tradeMethod;
        @Schema(description = "직거래 시에 거래 희망장소", example = "신대방삼거리역 1번 출구")
        private String tradeLocation;
        @Schema(description = "상품 설명", example = "뒷면에 미세한 찍힘이 있지만 (사진 첨부), 꺠끗하게 읽었습니다.")
        private String description;
        @Schema(description = "판매 상태", example = "판매중")
        private ProductStatus productStatus;
        @Schema(description = "상품 사진 목록", example = "/2025/03/07/second_hand_book.jpg")
        @Setter
        List<String> productImageList = new ArrayList<>();

        public static ProductResponse of(Product product, List<String> productImageList) {
            ProductResponse response = ProductResponse.builder().idx(product.getIdx())
                    .scoreCategory(ScoreCategory.toCategoryString(product.getMember().getScore()))
                    .sellerName(product.getMember().getNickname())
                    .book(product.getBook())
                    .category(product.getCategory())
                    .price(product.getPrice())
                    .bookCondition(product.getBookCondition())
                    .tradeMethod(product.getTradeMethod())
                    .tradeLocation(product.getTradeLocation())
                    .description(product.getDescription())
                    .productStatus(product.getProductStatus()).build();
            response.setProductImageList(productImageList);
            return response;
            // 응답에 null값 오류 없도록 시도할 코드 아래...
//            return builder()
//                    .idx(product.getIdx())
//                    .scoreCategory(ScoreCategory.toCategoryString(product.getMember().getScore()))
//                    .sellerName(product.getMember().getNickname())
//                    .book(product.getBook())
//                    .category(product.getCategory())
//                    .price(product.getPrice())
//                    .bookCondition(product.getBookCondition())
//                    .tradeMethod(product.getTradeMethod())
//                    .tradeLocation(product.getTradeLocation())
//                    .description(product.getDescription())
//                    .productStatus(product.getProductStatus())
//                    .productImageList(productImageList).build();
        }
        public static ProductResponse of(Product product) {
            return builder().idx(product.getIdx())
                    .scoreCategory(ScoreCategory.toCategoryString(product.getMember().getScore()))
                    .sellerName(product.getMember().getNickname())
                    .book(product.getBook())
                    .category(product.getCategory())
                    .price(product.getPrice())
                    .bookCondition(product.getBookCondition())
                    .tradeMethod(product.getTradeMethod())
                    .tradeLocation(product.getTradeLocation())
                    .description(product.getDescription())
                    .productStatus(product.getProductStatus()).build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "중고책 상품 목록 응답 DTO")
    public static class ListResponse {
        @Schema(description = "판매자 신뢰 점수", example = "180")
        private Integer sellerScore;
        @Schema(description = "상품 고유값", example="1")
        private Long productIdx;
        @Schema(description = "중고책 이름", example = "오래 준비해온 대답 (김영하의 시칠리아)")
        private String title;
        @Schema(description = "중고책 저자", example = "김영하")
        private String author;
        @Schema(description = "중고책 출판사", example = "복복서가")
        private String publisher;
        @Schema(description = "중고책 출판일", example = "2020-04-29")
        private LocalDate publicationDate;
        @Schema(description = "판매가", example = "5000")
        private Long price;
        @Schema(description = "책의 보존 상태", example = "GOOD")
        private BookCondition bookCondition;
        @Schema(description = "상품 대표 사진", example =  "/2025/03/07/second_hand_book.jpg")
        private String firstImageUrl;
        @Schema(description = "상품 찜 취소 여부", example = "true")
        private boolean isWishCanceled;
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    @Schema(description = "중고책 상품 삭제 응답 DTO")
    public static class DeleteResponse {
        @Setter
        @Schema(description = "삭제한 상품의 idx", example = "1")
        private Long idx;
    }
}
