package com.example.breadbook.domain.chat;

import com.example.breadbook.domain.chat.model.Message;
import com.example.breadbook.domain.chat.model.MessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Operation(summary = "채팅 메시지 전송", description = "특정 채팅방(roomIdx)에 메시지를 전송합니다.")
    @MessageMapping("/chat/{roomIdx}") // 프론트에서 "/app/chat/{roomIdx}"로 메시지를 보냄
    public void sendMessage(
            @Parameter(description = "채팅방 ID") @DestinationVariable Long roomIdx,
            @Parameter(description = "메시지 객체") Message message) {
        // DB에 메시지 저장
        Message savedMessage = chattingRoomService.saveMessage(roomIdx, message.getSendUserIdx(), message.getMessage());
        log.info("[DEBUG] 메시지 수신: {}", message);

        // DTO 변환 후 전송
        MessageDto.MessageRes messageDto = MessageDto.MessageRes.from(savedMessage);

        // 해당 채팅방을 구독한 클라이언트들에게 메시지 전송
        messagingTemplate.convertAndSend("/topic/room/" + roomIdx, messageDto);
    }
}
