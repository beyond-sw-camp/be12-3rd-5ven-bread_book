package com.example.breadbook.domain.order.model;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.domain.review.model.Review;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {


    @Getter
    public static class OrderDtoReq{
        private int amount;
        private Long memberIdx;
        private Long productIdx;
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
    @Builder
    public static class OrderListResp{
        private LocalDateTime orderCreatedAt;
        private OrderStatus orderStatus;
        private Long orderIdx;
        private String bookImg;
        private int amount;
        private String title;
        private String userName;
        private Long reviewIdx;
        private Long productIdx;
        public static OrderListResp toResp(Order order, Long reviewIdx){
            return OrderListResp.builder()
                    .orderIdx(order.getIdx())
                    .amount(order.getAmount())
                    .orderStatus(order.getOrderStatus())
                    .orderCreatedAt(order.getCreatedAt())
                    .bookImg(order.getProduct().getBookImage())
                    .productIdx(order.getProduct().getIdx())
                    .userName(order.getMember().getUsername())
                    .reviewIdx(reviewIdx)
                    .build();
        }
    }


    @Getter
    @Builder
    public static class PayListResp{
        private LocalDateTime orderCreatedAt;
        private OrderStatus orderStatus;
        private Long orderIdx;
        private String bookImg;
        private int amount;
        private String title;
        private String userName;
        private Long productIdx;
        public static PayListResp toResp(Order order){
            return PayListResp.builder()
                    .orderIdx(order.getIdx())
                    .amount(order.getAmount())
                    .orderStatus(order.getOrderStatus())
                    .orderCreatedAt(order.getCreatedAt())
                    .bookImg(order.getProduct().getBookImage())
                    .userName(order.getMember().getUsername())
                    .productIdx(order.getProduct().getIdx())
                    .build();
        }
    }



}
