package com.example.breadbook.domain.chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class ChattingRoomDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChattingRes {
        private Long roomIdx;
        private String identifier;
        private Long productIdx;
        private String title; // 추가
        private String productImageUrl; // 책 이미지 URL 추가
        private Long productPrice;
        private List<MessageDto.MessageRes> messages;

        public static ChattingRes from(ChattingRoom room) {
            return ChattingRes.builder()
                    .roomIdx(room.getIdx())
                    .identifier(room.getIdentifier())
                    .productIdx(room.getProductIdx())
                    .title(room.getTitle())
                    .productImageUrl(room.getProductImageUrl()) // 책 이미지
                    .productPrice(room.getProductPrice())
                    .messages(
                            room.getMessages() == null
                                    ? null : room.getMessages().stream().map(MessageDto.MessageRes::from).collect(Collectors.toList())
                    )
                    .build();
        }
    }
}
