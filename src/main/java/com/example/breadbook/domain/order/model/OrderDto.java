package com.example.breadbook.domain.order.model;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

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
        private void setReviewIdx(Long reviewIdx){
            this.reviewIdx = reviewIdx;
        }
        public static OrderListResp toResp(Order order){
            OrderListResp orderListResp= OrderListResp.builder()
                    .orderStatus(order.getOrderStatus())
                    .orderIdx(order.getIdx())
                    .amount(order.getAmount())
                    .bookImg(order.getProduct().getProductImageList().get(0).getProductImgUrl())
                    .orderCreatedAt(order.getCreatedAt())
                    .title(order.getProduct().getBook().getTitle())
                    .userName(order.getProduct().getMember().getUsername())
                    .productIdx(order.getProduct().getIdx())
                    .reviewIdx(order.getReview() != null ? order.getReview().getIdx() : null)
                    .build();

            return orderListResp;
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
                    .orderStatus(order.getOrderStatus())
                    .orderIdx(order.getIdx())
                    .amount(order.getAmount())
                    .bookImg(order.getProduct().getProductImageList().get(0).getProductImgUrl())
                    .orderCreatedAt(order.getCreatedAt())
                    .title(order.getProduct().getBook().getTitle())
                    .userName(order.getProduct().getMember().getUsername())
                    .productIdx(order.getProduct().getIdx())
                    .build();
        }
    }


    @Getter
    @Builder
    public static class OrderDetailsResp {
        private LocalDateTime orderCreatedAt ;
        private String bookImg;
        private OrderStatus orderStatus;
        private String title;
        private int amount;
        private Long orderIdx;
        private Long reviewIdx;
        private Long memberIdx;

        public static OrderDetailsResp toResp(Order order){
            return OrderDetailsResp.builder()
                    .orderCreatedAt(order.getCreatedAt())
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
