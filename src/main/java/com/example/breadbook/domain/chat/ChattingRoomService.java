package com.example.breadbook.domain.chat;

import com.example.breadbook.domain.chat.model.*;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.member.repository.MemberRepository;
import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.domain.product.repository.ProductRepository;
import com.example.breadbook.global.response.BaseResponse;
import com.example.breadbook.global.response.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChattingRoomService {
    private final ChattingRoomRepository chattingRoomRepository;
    private final MessageRepository messageRepository;
    private final ParticipantRepository participantRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    //  채팅방 생성 (identifier 자동 생성)

    @Transactional
    public ResponseEntity<BaseResponse<ChattingRoomDto.ChattingRes>> createChattingRoom(Long productIdx, Long buyerIdx) {
        try {
            Product product = productRepository.findById(productIdx)
                    .orElse(null);
            if (product == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_CREATE_PRODUCT_NULL));
            }

            Member buyer = memberRepository.findById(buyerIdx)
                    .orElse(null);
            if (buyer == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_CREATE_BUYER_NULL));
            }

            Member seller = product.getMember(); // 판매자 조회

            // 구매자와 판매자가 같은 경우 403 Forbidden 반환
            if (seller.getIdx().equals(buyerIdx)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_CREATE_INVALID_REQUEST));
            }

            // 기존 채팅방이 있는지 확인
            Optional<ChattingRoom> existingRoom = chattingRoomRepository.findByProductIdxAndBuyerIdx(productIdx, buyerIdx);
            if (existingRoom.isPresent()) {
                System.out.println("✅ 기존 채팅방 반환! ID: " + existingRoom.get().getIdx());
                return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_CREATE_SUCCESS, ChattingRoomDto.ChattingRes.from(existingRoom.get())));
            }

            ChattingRoom room = new ChattingRoom();
            room.setIdentifier(UUID.randomUUID().toString()); // UUID 기반 identifier 자동 생성
            room.setProduct(product);
            room.setBuyer(buyer);

            ChattingRoom savedRoom = chattingRoomRepository.save(room);

            // 채팅방 참여자 추가 (Bulk Insert)
            List<Participant> participants = List.of(
                    new Participant(savedRoom, buyer),
                    new Participant(savedRoom, seller)
            );
            participantRepository.saveAll(participants);

            return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_CREATE_SUCCESS, ChattingRoomDto.ChattingRes.from(savedRoom)));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_CREATE_NULL));
        }
    }

    // 모든 채팅방 조회
    @Transactional(readOnly = true)
    public ResponseEntity<BaseResponse<List<ChattingRoomDto.ChattingRes>>> getAllRooms() {
        try {
            List<ChattingRoom> rooms = chattingRoomRepository.findAll();

            if (rooms.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_ROOM_NULL));
            }

            List<ChattingRoomDto.ChattingRes> response = rooms.stream()
                    .map(ChattingRoomDto.ChattingRes::from)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_LIST_SUCCESS, response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_LIST_FAIL));
        }
    }

    // identifier 기반으로 특정 채팅방 및 메시지 조회
    @Transactional(readOnly = true)
    public ResponseEntity<BaseResponse<ChattingRoomDto.ChattingRes>> getRoomWithMessages(String identifier) {
        try {
            ChattingRoom room = chattingRoomRepository.findByIdentifier(identifier)
                    .orElse(null);
            if (room == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_ROOM_NULL));
            }

            List<Message> messages = messageRepository.findByRoom(room);
            room.setMessages(messages);

            return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_LIST_IDENTIFIER_SUCCESS, ChattingRoomDto.ChattingRes.from(room)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_LIST_IDENTIFIER_FAIL));
        }
    }

    // 특정 유저가 속한 채팅방 목록 조회
    @Transactional(readOnly = true)
    public ResponseEntity<BaseResponse<List<ChattingRoomDto.ChattingRes>>> getUserChatRooms(Long userIdx) {
        try {
            Member user = memberRepository.findById(userIdx)
                    .orElse(null);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_LIST_DETAIL_USER_FAIL));
            }

            List<Participant> participants = participantRepository.findByMember(user);
            List<ChattingRoomDto.ChattingRes> chatRooms = participants.stream()
                    .map(p -> ChattingRoomDto.ChattingRes.from(p.getRoom()))
                    .distinct()
                    .toList();

            return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_LIST_DETAIL_USER_SUCCESS, chatRooms));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_LIST_DETAIL_USER_FAIL));
        }
    }

}
