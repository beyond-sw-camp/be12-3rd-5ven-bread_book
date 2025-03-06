package com.example.breadbook.domain.chat.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MessageDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "채팅 메시지 응답 DTO")
    public static class MessageRes {
        @Schema(description = "메시지 ID", example = "1")
        private Long messageIdx;

        @Schema(description = "메시지를 보낸 사용자 ID", example = "5")
        private Long sendUserIdx;

        @Schema(description = "메시지 내용", example = "안녕하세요! 거래 가능할까요?")
        private String message;

        public static MessageRes from(Message msg) {
            return MessageRes.builder()
                    .messageIdx(msg.getIdx())
                    .sendUserIdx(msg.getSendUserIdx())
                    .message(msg.getMessage())
                    .build();
        }
    }
}
