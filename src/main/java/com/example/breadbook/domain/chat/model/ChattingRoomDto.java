package com.example.breadbook.domain.chat.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
    public static class ChattingReq {
        private Long productIdx;
        private Long userIdx;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "채팅방 응답 DTO")
    public static class ChattingRes {
        @Schema(description = "채팅방 ID", example = "1")
        private Long roomIdx;

        @Schema(description = "채팅방 식별자", example = "test_room_1")
        @NotBlank(message = "채팅방 식별자는 필수 입력값입니다.")
        private String identifier;

        @Schema(description = "채팅방 제목(상품명)", example = "한강")
        private String title;

        @Schema(description = "상품 대표 이미지 URL", example = "https://example.com/image.jpg")
        private String productImageUrl;

        @Schema(description = "상품 가격", example = "30000")
        private Long productPrice;

        @Schema(description = "채팅 메시지 목록")
        private List<MessageDto.MessageRes> messages;

        public static ChattingRes from(ChattingRoom room) {
            return ChattingRes.builder()
                    .roomIdx(room.getIdx())
                    .identifier(room.getIdentifier())
                    .title(room.getProduct().getBook().getTitle())
                    .productImageUrl(
                            !room.getProduct().getProductImageList().isEmpty()
                                    ? room.getProduct().getProductImageList().get(0).getProductImgUrl()
                                    : null
                    )
                    .productPrice(room.getProduct().getPrice())
                    .messages(
                            room.getMessages() == null
                                    ? null : room.getMessages().stream().map(MessageDto.MessageRes::from).collect(Collectors.toList())
                    )
                    .build();
        }
    }
}
