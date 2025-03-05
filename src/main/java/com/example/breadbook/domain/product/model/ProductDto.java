package com.example.breadbook.domain.product.model;

import com.example.breadbook.domain.book.model.Book;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.BookCondition;
import com.example.breadbook.domain.product.ProductStatus;
import com.example.breadbook.domain.product.ScoreCategory;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductDto {
    @Getter
    public static class ProductRegister {
        // private Member member; //jwt 토큰
        // private Book book; //frontend 사용자 선택 => json 스트링 값 => 어떻게 바꿀 것?
        // private Category category; // frontend 사용자 선택 => json 스트링값 => 어떻게 바꿀 것?
        private Long bookIdx;
        private String categoryName;
        private Long price;
        private BookCondition bookCondition;
        private String tradeMethod;
        private String tradeLocation;
        private String description;
        private ProductStatus productStatus;

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
                    .productStatus(productStatus)
                    .build();
        }
    }

    @Getter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ProductResponse {
        private Long idx;
        private String scoreCategory;
        private String sellerName;
        private Book book;
        private Category category;
        private Long price;
        private BookCondition bookCondition;
        private String tradeMethod;
        private String tradeLocation;
        private String description;
        private ProductStatus productStatus;
        @Setter
        List<String> productImageList = new ArrayList<>();

        public static ProductResponse of(Product product, List<String> productImageList) {
            return ProductResponse.builder()
                    .idx(product.getIdx())
                    .scoreCategory(ScoreCategory.toCategoryString(product.getMember().getScore()))
                    .sellerName(product.getMember().getNickname())
                    .book(product.getBook())
                    .category(product.getCategory())
                    .price(product.getPrice())
                    .bookCondition(product.getBookCondition())
                    .tradeMethod(product.getTradeMethod())
                    .tradeLocation(product.getTradeLocation())
                    .description(product.getDescription())
                    .productStatus(product.getProductStatus())
                    .productImageList(
                            productImageList == null?
                                    null : product.getProductImageList().stream()
                                        .map(ProductImage::getProductImgUrl)
                                        .toList())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ListResponse {
        private Integer sellerScore;
        private String title;
        private String author;
        private String publisher;
        private LocalDate publicationDate;
        private Long price;
        private BookCondition bookCondition;
        private String firstImageUrl;
        private boolean isWishCanceled;
    }

}
