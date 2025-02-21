package com.example.breadbook.domain.notification.model;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

public class NotificationDto {
    // 이부분은 유저 밑에 만들어두면 좋을 것 같다.
    // 유저의 알람이기에......
    // member에서 List<NotificationRegister> notifications = new ArrayList<>();
    // 이런식으로 전달받아 사용하는 편이 더 나을 듯하다.

    @Getter
    public static class NotificationRegister {
        private String message;
        private Boolean is_read;
        private Date created_at;

        public Notification toEntity(Member member, Product product) {
            return Notification.builder()
                    .message(message)
                    .is_read(is_read)
                    .created_at(created_at)
                    .member(member)
                    .product(product)
                    .build();
        }
    }

    @Getter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class NotificationResponse {
        private Long idx;
        private Long member_Idx;
        private Long product_Idx;
        private String message;
        private Boolean is_read;
        private Date created_at;


        public static NotificationResponse from(Notification notification) {
            // 이부분은 물어보고 추후에 수정하자.
            Product product = notification.getProduct();
            return NotificationResponse.builder()
                    .idx(notification.getIdx())
                    .member_Idx(product.getMemberIdx())
                    .product_Idx(product.getIdx())
                    .message(notification.getMessage())
                    .is_read(notification.getIs_read())
                    .created_at(notification.getCreated_at())
                    .build();
        }
    }
}
