package com.example.breadbook.domain.chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class MessageDto {

    @Getter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class MessageRes {
        private Long messageIdx;
//        private Long roomIdx;
        private Long sendUserIdx;
        private String message;
        public static MessageRes from(Message msg) {
            return MessageRes.builder()
                    .messageIdx(msg.getIdx())
//                    .roomIdx(msg.getRoom().getIdx())
                    .sendUserIdx(msg.getSendUserIdx())
                    .message(msg.getMessage())
                    .build();
        }
    }

}
