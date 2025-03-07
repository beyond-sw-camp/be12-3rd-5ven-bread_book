package com.example.breadbook.domain.chat.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

        @NotNull(message = "메시지 ID는 필수 입력값입니다.")
        @Min(value = 1, message = "메시지 ID는 1 이상이어야 합니다.")
        @Schema(description = "메시지 ID", example = "1")
        private Long messageIdx;

        @NotNull(message = "보낸 사용자 ID는 필수 입력값입니다.")
        @Min(value = 1, message = "보낸 사용자 ID는 1 이상이어야 합니다.")
        @Schema(description = "메시지를 보낸 사용자 ID", example = "5")
        private Long sendUserIdx;

        @NotBlank(message = "메시지 내용은 필수 입력값입니다.")
        @Size(min = 1, max = 500, message = "메시지 내용은 1자 이상 100자 이하이어야 합니다.")
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
