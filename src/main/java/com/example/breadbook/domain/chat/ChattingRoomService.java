package com.example.breadbook.domain.chat;

import com.example.breadbook.domain.book.BookRepository;
import com.example.breadbook.domain.chat.model.ChattingRoom;
import com.example.breadbook.domain.chat.model.Message;
import com.example.breadbook.domain.chat.model.Participant;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.member.repository.MemberRepository;
import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChattingRoomService {
    private final ChattingRoomRepository chattingRoomRepository;
    private final MessageRepository messageRepository;
    private final ParticipantRepository participantRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    // ✅ 채팅방 생성 (identifier 자동 생성)
    @Transactional
    public ChattingRoom createChattingRoom(Long productIdx, Long buyerIdx) {
        Product product = productRepository.findById(productIdx)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        // ✅ 동일한 productIdx와 buyerId로 생성된 채팅방이 있는지 확인 (중복 방지)
        Optional<ChattingRoom> existingRoom = chattingRoomRepository.findByProductIdxAndBuyerIdx(productIdx, buyerIdx);
        if (existingRoom.isPresent()) {
            return existingRoom.get(); // 이미 존재하는 채팅방 반환
        }

        // ✅ 새로운 채팅방 생성
        Member buyer = memberRepository.findById(buyerIdx)
                .orElseThrow(() -> new IllegalArgumentException("구매자를 찾을 수 없습니다."));

        Member seller = product.getMember(); // ✅ 상품의 판매자 조회

        ChattingRoom room = new ChattingRoom();
        room.setIdentifier(UUID.randomUUID().toString()); // ✅ UUID 기반 identifier 자동 생성
        room.setProduct(product);
        room.setBuyer(buyer); // ✅ buyer 저장 (성능 최적화)

        ChattingRoom savedRoom = chattingRoomRepository.save(room);

        // ✅ 채팅방 참여자 추가
        List<Participant> participants = List.of(
                new Participant(savedRoom, buyer),
                new Participant(savedRoom, seller)
        );
        participantRepository.saveAll(participants); // ✅ 성능 최적화 (Bulk Insert)

        return savedRoom;
    }


    // 모든 채팅방 조회
    public List<ChattingRoom> getAllRooms() {
        return chattingRoomRepository.findAll();
    }

    // ✅ identifier 기반으로 특정 채팅방 및 메시지 조회
    @Transactional(readOnly = true)
    public ChattingRoom getRoomWithMessages(String identifier) {
        ChattingRoom room = chattingRoomRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));
        List<Message> messages = messageRepository.findByRoom(room);
        room.setMessages(messages);
        return room;
    }

    // 메시지 저장
    @Transactional
    public Message saveMessage(Long roomIdx, Long userIdx, String messageText) {
        ChattingRoom room = chattingRoomRepository.findById(roomIdx)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));
        Member sender = memberRepository.findById(userIdx)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Message message = new Message();
        message.setRoom(room);
        message.setSendUserIdx(sender.getIdx());
        message.setMessage(messageText);

        return messageRepository.save(message);
    }

    // 특정 유저가 속한 채팅방 목록 조회
    @Transactional(readOnly = true)
    public List<ChattingRoom> getUserChatRooms(Long userIdx) {
        Member user = memberRepository.findById(userIdx)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        List<Participant> participants = participantRepository.findByMember(user);
        return participants.stream()
                .map(Participant::getRoom)
                .distinct()
                .toList();
    }
}
