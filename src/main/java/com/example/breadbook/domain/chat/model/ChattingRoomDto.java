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
        private String title;
        private String productImageUrl;
        private Long productPrice;
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

