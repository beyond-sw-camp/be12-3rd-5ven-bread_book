package com.example.breadbook.domain.chat;

import com.example.breadbook.domain.chat.model.ChattingRoom;
import com.example.breadbook.domain.chat.model.ChattingRoomDto;
import com.example.breadbook.global.response.BaseResponse;
import com.example.breadbook.global.response.BaseResponseMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chatting")
@RequiredArgsConstructor
public class ChattingRoomController {
    private final ChattingRoomService chattingRoomService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    //  채팅방 생성 (상품을 올린 판매자와 구매자 간 1:1 채팅)
    @PostMapping("/room")
    public ResponseEntity<BaseResponse<ChattingRoomDto.ChattingRes>> createRoom(
            @RequestParam String identifier,
            @RequestParam Long productIdx,
            @RequestParam Long buyerId,
            @RequestParam Long sellerId) {

        ChattingRoom room = chattingRoomService.createChattingRoom(identifier, productIdx, buyerId, sellerId);

//        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.CHATTINGROOM_SUCCESS, ChattingRoomDto.ChattingRes.from(room));
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_SUCCESS, ChattingRoomDto.ChattingRes.from(room)));

    }



    //  채팅방 목록 조회
    @GetMapping("/rooms")
    public ResponseEntity<BaseResponse<List<ChattingRoomDto.ChattingRes>>> getAllRooms() {
        List<ChattingRoomDto.ChattingRes> rooms = chattingRoomService.getAllRooms().stream()
                .map(ChattingRoomDto.ChattingRes::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new BaseResponse(BaseResponseMessage.CHATTINGROOM_LIST_SUCCESS, rooms));
    }

    //  특정 채팅방 정보 및 메시지 조회
    @GetMapping("/room/{roomIdx}")
    public ResponseEntity<BaseResponse<ChattingRoomDto.ChattingRes>> getRoomDetails(@PathVariable Long roomIdx) {
        ChattingRoomDto.ChattingRes room = ChattingRoomDto.ChattingRes.from(chattingRoomService.getRoomWithMessages(roomIdx));
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_LIST_DETAIL_SUCCESS, room));
    }

    // 특정 유저의 채팅방 목록 조회
    @GetMapping("/rooms/{userIdx}")
    public ResponseEntity<BaseResponse<List<ChattingRoomDto.ChattingRes>>> getUserChatRooms(@PathVariable Long userIdx) {
        List<ChattingRoomDto.ChattingRes> rooms = chattingRoomService.getUserChatRooms(userIdx).stream()
                .map(ChattingRoomDto.ChattingRes::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_LIST_SUCCESS, rooms));
    }


}
