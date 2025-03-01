package com.example.breadbook.domain.product.model;

import com.example.breadbook.domain.book.model.Book;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.BookCondition;
import com.example.breadbook.domain.product.ProductStatus;
import lombok.*;

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
        private Long categoryIdx;
        private Long price;
        private BookCondition bookCondition;
        private String tradeMethod;
        private String tradeLocation;
        private String description;
        private ProductStatus productStatus;
        List<String> productImageList = new ArrayList<>();

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
    @Getter
    public static class ProductImageRegister {
        private String productImgUrl;

        public ProductImage toEntity(Product product) {
            return ProductImage.builder()
                    .productImgUrl(productImgUrl)
                    .product(product)
                    .build();
        }
    }

    @Getter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ProductResponse {
        private Long idx;
        private String authorName;

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

        public static ProductResponse of(Product product) {
            return ProductResponse.builder()
                    .idx(product.getIdx())
                    .authorName(product.getMember().getNickname())
                    .book(product.getBook())
                    .category(product.getCategory())
                    .price(product.getPrice())
                    .bookCondition(product.getBookCondition())
                    .tradeMethod(product.getTradeMethod())
                    .tradeLocation(product.getTradeLocation())
                    .description(product.getDescription())
                    .productStatus(product.getProductStatus())
                    .productImageList(
                            product.getProductImageList() == null?
                                    null : product.getProductImageList().stream()
                                        .map(image-> image.getProductImgUrl())
                                        .toList())
                    .build();
        }
    }

//    @Getter @NoArgsConstructor @AllArgsConstructor @Builder
//    public static class ProductImageResponse {
//        private Long idx;
//        private String productImgUrl;
//
//        public static ProductImageResponse from(ProductImage productImage) {
//            return ProductImageResponse.builder()
//                    .idx(productImage.getIdx())
//                    .productImgUrl(productImage.getProductImgUrl())
//                    .build();
//        }
//    }
}
