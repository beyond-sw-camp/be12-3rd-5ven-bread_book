package com.example.breadbook.domain.chat;

import com.example.breadbook.domain.chat.event.ChatMessageEvent;
import com.example.breadbook.domain.chat.model.Message;
import com.example.breadbook.domain.chat.model.MessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "ChattingWebSocket", description = "웹소켓 채팅 컨트롤러")
public class ChattingWebSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChattingRoomService chattingRoomService;
    private final KafkaTemplate<String, ChatMessageEvent> kafkaTemplate;

    @MessageMapping("/chat/{roomIdx}")
    public void sendMessage(
            @DestinationVariable Long roomIdx,
            Message message) {
        log.info("[DEBUG] 메시지 수신: {}", message);

        // Kafka로 메시지 전송 (DB에 저장 X)
        ChatMessageEvent event = new ChatMessageEvent(roomIdx, message.getSendUserIdx(), message.getMessage());
        kafkaTemplate.send("chat-message-topic", event);
    }

}
