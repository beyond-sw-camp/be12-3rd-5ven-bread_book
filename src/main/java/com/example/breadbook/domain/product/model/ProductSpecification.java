package com.example.breadbook.domain.product.model;

import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> hasTitle(String title) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get("book").get("title"), "%" + title + "%");
    }
    public static Specification<Product> hasAuthor(String author) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get("author"), "%" + author + "%");
    }
    public static Specification<Product> hasPubliser(String publisher) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get("publisher"), "%" + publisher + "%");
    }
    public static Specification<Product> hasCategory(String category) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category"), category);
    }
    // 키워드 검색 (제목, 저자, 출판사에 모두 적용)
    public static Specification<Product> hasKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            String likePattern = "%" + keyword + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("book").get("title"), likePattern),
                    criteriaBuilder.like(root.get("book").get("author"), likePattern),
                    criteriaBuilder.like(root.get("book").get("publisher"), likePattern)
            );
        };
    }
}
