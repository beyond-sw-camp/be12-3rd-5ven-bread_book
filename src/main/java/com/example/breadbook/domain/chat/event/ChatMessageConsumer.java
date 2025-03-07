package com.example.breadbook.domain.chat.event;

import com.example.breadbook.domain.chat.ChattingRoomRepository;
import com.example.breadbook.domain.chat.MessageRepository;
import com.example.breadbook.domain.chat.model.ChattingRoom;
import com.example.breadbook.domain.chat.model.Message;
import com.example.breadbook.domain.chat.model.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChatMessageConsumer {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageRepository messageRepository;
    private final ChattingRoomRepository chattingRoomRepository;

    @KafkaListener(
            topics = "chat-message-topic",
            properties = {
                    "spring.json.value.default.type:com.example.breadbook.domain.chat.event.ChatMessageEvent",
                    "spring.json.use.type.headers:false"
            }
    )
    public void consumeChatMessage(ChatMessageEvent event) {
        log.info("Kafka 메시지 수신: {}", event);

        // 채팅방 존재 여부 확인
        Optional<ChattingRoom> optionalRoom = chattingRoomRepository.findById(event.getRoomIdx());
        if (optionalRoom.isEmpty()) {
            log.error("채팅방이 존재하지 않습니다. roomIdx={}", event.getRoomIdx());
            return;
        }

        ChattingRoom room = optionalRoom.get();

        // 메시지 저장 (여기서만 저장)
        Message message = new Message();
        message.setRoom(room);
        message.setSendUserIdx(event.getSenderIdx());
        message.setMessage(event.getMessage());
        message.setIsRead(false);
        messageRepository.save(message);

        log.info("메시지 저장 완료: {}", message.getIdx());

        // lastChat 업데이트
        room.setLastChat(event.getMessage());
        chattingRoomRepository.save(room);

        log.info("채팅방 lastChat 업데이트: {}", room.getLastChat());

        // WebSocket으로 클라이언트에 전송
        messagingTemplate.convertAndSend("/topic/room/" + event.getRoomIdx(), MessageDto.MessageRes.from(message));
    }
}