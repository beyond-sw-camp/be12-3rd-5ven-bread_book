//package com.example.breadbook.domain.chat;
//
//import com.example.breadbook.domain.book.BookRepository;
//import com.example.breadbook.domain.chat.model.ChattingRoom;
//import com.example.breadbook.domain.chat.model.Message;
//import com.example.breadbook.domain.chat.model.Participant;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class ChattingRoomService {
//    private final ChattingRoomRepository chattingRoomRepository;
//    private final MessageRepository messageRepository;
//    private final ParticipantRepository participantRepository;
//    private final BookRepository bookRepository;
//
//    // 1:1 채팅방 생성 (상품을 등록한 판매자와 구매자만 참여)
////    public ChattingRoom createChattingRoom(String identifier, Long productIdx, Long buyerId, Long sellerId) {
////        ChattingRoom room = new ChattingRoom();
////        room.setIdentifier(identifier);
////        room.setProductIdx(productIdx);
////        ChattingRoom savedRoom = chattingRoomRepository.save(room);
////
////        // 채팅방 생성 시 자동으로 구매자 & 판매자 추가
////        participantRepository.save(new Participant(savedRoom, buyerId));
////        participantRepository.save(new Participant(savedRoom, sellerId));
////
////        return savedRoom;
////    }
//    public ChattingRoom createChattingRoom(String identifier, Long productIdx, Long buyerId, Long sellerId) {
//        ChattingRoom room = new ChattingRoom();
//        room.setIdentifier(identifier);
//        room.setProductIdx(productIdx);
//
//        // 📌 책 제목 가져오기
//        bookRepository.findById(productIdx).ifPresent(book -> {
//            room.setTitle(book.getTitle()); // 채팅방 제목 설정
//        });
//
//        ChattingRoom savedRoom = chattingRoomRepository.save(room);
//
//        // 채팅방 생성 시 자동으로 구매자 & 판매자 추가
//        participantRepository.save(new Participant(savedRoom, buyerId));
//        participantRepository.save(new Participant(savedRoom, sellerId));
//
//        return savedRoom;
//    }
//
//    // 채팅방 목록 조회
//    public List<ChattingRoom> getAllRooms() {
//        return chattingRoomRepository.findAll();
//    }
//
//    // 특정 채팅방 및 메시지 조회
//    public ChattingRoom getRoomWithMessages(Long roomIdx) {
//        ChattingRoom room = chattingRoomRepository.findById(roomIdx)
//                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));
//        List<Message> messages = messageRepository.findByRoom(room);
//        room.setMessages(messages);
//        return room;
//    }
//
//    //  메시지 저장
//    public Message saveMessage(Long roomIdx, Long userIdx, String messageText) {
//        ChattingRoom room = chattingRoomRepository.findById(roomIdx)
//                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));
//        Message message = new Message();
//        message.setRoom(room);
//        message.setSendUserIdx(userIdx);
//        message.setMessage(messageText);
//        return messageRepository.save(message);
//    }
//
//    // 로그인한 유저가 포함된 채팅방 목록 조회
//    public List<ChattingRoom> getUserChatRooms(Long userIdx) {
//        List<Participant> participants = participantRepository.findAll();
//        return participants.stream()
//                .filter(p -> p.getUserIdx().equals(userIdx)) // 현재 로그인한 유저가 속한 방만 필터링
//                .map(Participant::getRoom)
//                .distinct()
//                .toList();
//    }
//
//
//}


package com.example.breadbook.domain.chat;

import com.example.breadbook.domain.book.BookRepository;
import com.example.breadbook.domain.chat.model.ChattingRoom;
import com.example.breadbook.domain.chat.model.Message;
import com.example.breadbook.domain.chat.model.Participant;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.member.repository.MemberRepository;
import com.example.breadbook.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChattingRoomService {
    private final ChattingRoomRepository chattingRoomRepository;
    private final MessageRepository messageRepository;
    private final ParticipantRepository participantRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    // 1:1 채팅방 생성 (판매자 & 구매자)
    @Transactional
    public ChattingRoom createChattingRoom(String identifier, Long productIdx, Long buyerId, Long sellerId) {
        ChattingRoom room = new ChattingRoom();
        room.setIdentifier(identifier);
        room.setProductIdx(productIdx);

        // 책 제목 가져오기
//        bookRepository.findById(productIdx).ifPresent(book -> room.setTitle(book.getTitle()));

        // 📌 Product 엔티티에서 책 제목 & 대표 이미지 가져오기
        productRepository.findById(productIdx).ifPresent(product -> {
            room.setTitle(product.getBook().getTitle()); // ✅ 책 제목 설정
            room.setProductPrice(product.getPrice());

            // ✅ 대표 이미지 가져오기 (첫 번째 이미지 선택)
            if (!product.getProductImageList().isEmpty()) {
                room.setProductImageUrl(product.getProductImageList().get(0).getProductImgUrl());
            } else {
                room.setProductImageUrl(null); // 이미지가 없으면 null
            }
        });


        ChattingRoom savedRoom = chattingRoomRepository.save(room);

        // Member 조회
        Member buyer = memberRepository.findById(buyerId)
                .orElseThrow(() -> new IllegalArgumentException("구매자를 찾을 수 없습니다."));
        Member seller = memberRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("판매자를 찾을 수 없습니다."));

        // 채팅방 참여자 추가
        participantRepository.save(new Participant(savedRoom, buyer));
        participantRepository.save(new Participant(savedRoom, seller));

        return savedRoom;
    }

    // 모든 채팅방 조회
    public List<ChattingRoom> getAllRooms() {
        return chattingRoomRepository.findAll();
    }

    // 특정 채팅방 및 메시지 조회
    @Transactional(readOnly = true)
    public ChattingRoom getRoomWithMessages(Long roomIdx) {
        ChattingRoom room = chattingRoomRepository.findById(roomIdx)
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
