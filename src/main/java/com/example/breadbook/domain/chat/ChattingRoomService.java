package com.example.breadbook.domain.chat;

import com.example.breadbook.domain.chat.model.ChattingRoom;
import com.example.breadbook.domain.chat.model.Message;
import com.example.breadbook.domain.chat.model.Participant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChattingRoomService {
    private final ChattingRoomRepository chattingRoomRepository;
    private final MessageRepository messageRepository;
    private final ParticipantRepository participantRepository;

    // 1:1 채팅방 생성 (상품을 등록한 판매자와 구매자만 참여)
    public ChattingRoom createChattingRoom(String identifier, Long productIdx, Long buyerId, Long sellerId) {
        ChattingRoom room = new ChattingRoom();
        room.setIdentifier(identifier);
        room.setProductIdx(productIdx);
        ChattingRoom savedRoom = chattingRoomRepository.save(room);

        // 채팅방 생성 시 자동으로 구매자 & 판매자 추가
        participantRepository.save(new Participant(savedRoom, buyerId));
        participantRepository.save(new Participant(savedRoom, sellerId));

        return savedRoom;
    }

    // 채팅방 목록 조회
    public List<ChattingRoom> getAllRooms() {
        return chattingRoomRepository.findAll();
    }

    // 특정 채팅방 및 메시지 조회
    public ChattingRoom getRoomWithMessages(Long roomIdx) {
        ChattingRoom room = chattingRoomRepository.findById(roomIdx)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));
        List<Message> messages = messageRepository.findByRoom(room);
        room.setMessages(messages);
        return room;
    }

    //  메시지 저장
    public Message saveMessage(Long roomIdx, Long userIdx, String messageText) {
        ChattingRoom room = chattingRoomRepository.findById(roomIdx)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));
        Message message = new Message();
        message.setRoom(room);
        message.setSendUserIdx(userIdx);
        message.setMessage(messageText);
        return messageRepository.save(message);
    }
}
