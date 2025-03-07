package com.example.breadbook.domain.chat.event;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatMessageEvent {
    private Long roomIdx;
    private Long senderIdx;
    private String message;

    public ChatMessageEvent toEntity() {
        return ChatMessageEvent.builder()
                .roomIdx(roomIdx)
                .senderIdx(senderIdx)
                .message(message)
                .build();
    }
}
