package com.example.breadbook.domain.product.model;

import com.example.breadbook.domain.book.model.BookDto;
import com.example.breadbook.domain.category.model.Category;
import com.example.breadbook.domain.product.BookCondition;
import lombok.Getter;

import java.math.BigDecimal;

public class ProductDto {
    @Getter
    public static class CreateRequest {
        private Long memberIdx; // jwt 토큰을 활용 .. ?
        private BookDto bookIdx; // Book
        private Category category; // frontend 사용자 선택 => json 스트링값
        private BigDecimal price;
        private BookCondition bookCondition;
        private String tradeMethod;
        private String tradeLocation;

    }
}
