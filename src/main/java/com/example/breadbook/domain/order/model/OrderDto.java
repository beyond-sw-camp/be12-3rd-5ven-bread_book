package com.example.breadbook.domain.order.model;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.ProductStatus;
import com.example.breadbook.domain.product.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderDto {

    @Getter
    public static class OrderDtoReq{
        @Schema(description = "구매하려는 상품의 가격", example = "20000")
        private int amount;
        @Schema(description = "판매자와의 채팅방 번호", example = "1")
        private Long chattingRoom;
    }

    @Getter
    public static class OrderRegistResp{
        public static Order toEntity(Member member, Product product, int amount){
            return Order.builder()
                    .orderStatus(OrderStatus.품절)
                    .member(member)
                    .product(product)
                    .amount(amount)
                    .createdAt(LocalDateTime.now())
                    .build();
        }
    }

    @Getter
    public static class OrderListReq{
        @Schema(description = "주문 정보가 기록된 페이지", example = "0")
        private int page;
    }

    @Getter
    @Builder
    public static class OrderListResp{
        @Schema(description = "주문 시간", example = "2024-02-26")
        private String orderCreatedAt;
        @Schema(description = "주문 상태", example = "거래중")
        private OrderStatus orderStatus;
        @Schema(description = "주문 번호", example = "1")
        private Long orderIdx;
        @Schema(description = "책 이미지", example = "https://shopping-phinf.pstatic.net/main_3244164/32441644071.20221019140242.jpg?type=w300")
        private String bookImg;
        @Schema(description = "각격", example = "20000")
        private int amount;
        @Schema(description = "책 제목", example = "어린왕자")
        private String title;
        @Schema(description = "판매자", example = "홍길동")
        private String userName;
        @Schema(description = "리뷰 정보", example = "1")
        private Long reviewIdx;
        @Schema(description = "상품 번호", example = "1")
        private Long productIdx;
        public static OrderListResp of(Product product){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            Order order = product.getOrders().stream().findFirst().orElse(null);
            OrderListResp orderListResp= OrderListResp.builder()
                    .orderStatus(order.getOrderStatus())
                    .orderIdx(order.getIdx())
                    .amount(order.getAmount())
                    .bookImg(product.getBook().getBookImageUrl())
                    .orderCreatedAt(order.getCreatedAt().format(formatter))
                    .title(product.getBook().getTitle())
                    .userName(product.getMember().getUsername())
                    .productIdx(product.getIdx())
                    .reviewIdx(order.getReview() != null ? order.getReview().getIdx() : null)
                    .build();

            return orderListResp;
        }
    }


    @Getter
    @Builder
    public static class PayListResp{
        @Schema(description = "주문 시간", example = "2024-02-26")
        private String orderCreatedAt;
        @Schema(description = "판매 상태", example = "거래_완료")
        private ProductStatus productStatus;
        @Schema(description = "주문 번호", example = "1")
        private Long orderIdx;
        @Schema(description = "책 이미지", example = "https://shopping-phinf.pstatic.net/main_3244164/32441644071.20221019140242.jpg?type=w300")
        private String bookImg;
        @Schema(description = "가격", example = "20000")
        private int amount;
        @Schema(description = "책 제목", example = "어린왕자")
        private String title;
        @Schema(description = "판매자", example = "홍길동")
        private String userName;
        @Schema(description = "상품 번호", example = "1")
        private Long productIdx;
        public static PayListResp of(Product product){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            Order order = product.getOrders().stream().findFirst().orElse(null);

            return PayListResp.builder()
                    .productStatus(product.getProductStatus())
                    .orderIdx(order.getIdx())
                    .amount(order.getAmount())
                    .bookImg(product.getBook().getBookImageUrl())
                    .orderCreatedAt(order.getCreatedAt().format(formatter))
                    .title(product.getBook().getTitle())
                    .userName((order != null && !product.getOrders().isEmpty()) ? order.getMember().getUsername() : null)
                    .productIdx(product.getIdx())
                    .build();
        }
    }


    @Getter
    @Builder
    public static class OrderDetailsResp {
        @Schema(description = "주문 시간", example = "2024-02-26")
        private String orderCreatedAt ;
        @Schema(description = "책 이미지", example = "https://shopping-phinf.pstatic.net/main_3244164/32441644071.20221019140242.jpg?type=w300")
        private String bookImg;
        @Schema(description = "주문 상태", example = "품절")
        private OrderStatus orderStatus;
        @Schema(description = "책 제목", example = "어린왕자")
        private String title;
        @Schema(description = "가격", example = "20000")
        private int amount;
        @Schema(description = "상품 번호", example = "1")
        private Long orderIdx;
        @Schema(description = "주문 번호", example = "1")
        private Long reviewIdx;
        @Schema(description = "판매자 정보", example = "임꺽정")
        private Long memberIdx;

        public static OrderDetailsResp toResp(Order order){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return OrderDetailsResp.builder()
                    .orderCreatedAt(order.getCreatedAt().format(formatter))
                    .bookImg(order.getProduct().getProductImageList().get(0).getProductImgUrl())
                    .orderStatus(order.getOrderStatus())
                    .title(order.getProduct().getBook().getTitle())
                    .amount(order.getAmount())
                    .orderIdx(order.getIdx())
                    .memberIdx(order.getProduct().getMember().getIdx())
                    .reviewIdx(order.getReview() != null ? order.getReview().getIdx() : null)
                    .build();
        }
    }
}
