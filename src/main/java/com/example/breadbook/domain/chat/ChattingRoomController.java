package com.example.breadbook.domain.chat;

import com.example.breadbook.domain.chat.model.ChattingRoom;
import com.example.breadbook.domain.chat.model.ChattingRoomDto;
import com.example.breadbook.global.response.BaseResponse;
import com.example.breadbook.global.response.BaseResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "ChattingRoom", description = "채팅방 컨트롤러")
@RestController
@RequestMapping("/chatting")
@RequiredArgsConstructor
public class ChattingRoomController {
    private final ChattingRoomService chattingRoomService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Operation(summary = "채팅방 생성", description = "판매자와 구매자 간의 1:1 채팅방을 생성합니다.")
    @PostMapping("/room")
    public ResponseEntity<BaseResponse<ChattingRoomDto.ChattingRes>> createRoom(
            @Parameter(description = "상품 ID") @RequestParam Long productIdx,
            @Parameter(description = "구매자 ID") @RequestParam Long buyerId) {

        ChattingRoom room = chattingRoomService.createChattingRoom(productIdx, buyerId);
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_SUCCESS, ChattingRoomDto.ChattingRes.from(room)));
    }

    @Operation(summary = "채팅방 확인 또는 생성", description = "상품 ID와 구매자 ID를 기반으로 기존 채팅방이 있는지 확인하고, 없으면 새로 생성합니다.")
    @PostMapping("/rooms/check")
    public ResponseEntity<BaseResponse<ChattingRoomDto.ChattingRes>> getOrCreateChatRoom(@RequestBody ChattingRoomDto.ChattingReq request) {
        ChattingRoom room = chattingRoomService.createChattingRoom(request.getUserIdx(), request.getProductIdx());
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_SUCCESS, ChattingRoomDto.ChattingRes.from(room))); // ✅ identifier 반환
    }


    @Operation(summary = "채팅방 목록 조회", description = "모든 채팅방 목록을 조회합니다.")
    @GetMapping("/rooms")
    public ResponseEntity<BaseResponse<List<ChattingRoomDto.ChattingRes>>> getAllRooms() {
        List<ChattingRoomDto.ChattingRes> rooms = chattingRoomService.getAllRooms().stream()
                .map(ChattingRoomDto.ChattingRes::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_LIST_SUCCESS, rooms));
    }

    @Operation(summary = "특정 채팅방 정보 및 메시지 조회", description = "채팅방 ID를 통해 특정 채팅방의 상세 정보를 가져옵니다.")
    @GetMapping("/room/{identifier}")
    public ResponseEntity<BaseResponse<ChattingRoomDto.ChattingRes>> getRoomDetails(
            @Parameter(description = "채팅방 ID") @PathVariable String identifier) {
        ChattingRoomDto.ChattingRes room = ChattingRoomDto.ChattingRes.from(chattingRoomService.getRoomWithMessages(identifier));
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_LIST_DETAIL_SUCCESS, room));
    }

    @Operation(summary = "특정 유저의 채팅방 목록 조회", description = "사용자의 ID를 통해 해당 사용자의 채팅방 목록을 조회합니다.")
    @GetMapping("/rooms/{userIdx}")
    public ResponseEntity<BaseResponse<List<ChattingRoomDto.ChattingRes>>> getUserChatRooms(
            @Parameter(description = "사용자 ID") @PathVariable Long userIdx) {
        List<ChattingRoomDto.ChattingRes> rooms = chattingRoomService.getUserChatRooms(userIdx).stream()
                .map(ChattingRoomDto.ChattingRes::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new BaseResponse<>(BaseResponseMessage.CHATTINGROOM_LIST_SUCCESS, rooms));
    }
}
