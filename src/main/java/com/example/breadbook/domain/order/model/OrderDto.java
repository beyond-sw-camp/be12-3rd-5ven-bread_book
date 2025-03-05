package com.example.breadbook.domain.order.model;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderDto {


    @Getter
    public static class OrderDtoReq{
        private int amount;
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
    @Builder
    public static class OrderListResp{
        private String orderCreatedAt;
        private OrderStatus orderStatus;
        private Long orderIdx;
        private String bookImg;
        private int amount;
        private String title;
        private String userName;
        private Long reviewIdx;
        private Long productIdx;
        public static OrderListResp of(Order order){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


            OrderListResp orderListResp= OrderListResp.builder()
                    .orderStatus(order.getOrderStatus())
                    .orderIdx(order.getIdx())
                    .amount(order.getAmount())
                    .bookImg(order.getProduct().getProductImageList().get(0).getProductImgUrl())
                    .orderCreatedAt(order.getCreatedAt().format(formatter))
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
        private String orderCreatedAt;
        private OrderStatus orderStatus;
        private Long orderIdx;
        private String bookImg;
        private int amount;
        private String title;
        private String userName;
        private Long productIdx;
        public static PayListResp of(Product product){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            Order order = product.getOrders().stream().findFirst().orElse(null);

            return PayListResp.builder()
                    .orderStatus(order.getOrderStatus())
                    .orderIdx(order.getIdx())
                    .amount(order.getAmount())
                    .bookImg(product.getProductImageList().get(0).getProductImgUrl())
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
