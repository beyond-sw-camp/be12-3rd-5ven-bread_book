package com.example.breadbook.domain.chat;

import com.example.breadbook.domain.chat.model.ChattingRoomDto;
import com.example.breadbook.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "채팅 기능", description = "채팅방 컨트롤러")
@RestController
@RequestMapping("/chatting")
@RequiredArgsConstructor
public class ChattingRoomController {
    private final ChattingRoomService chattingRoomService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Operation(summary = "채팅방 생성", description = "판매자와 구매자 간의 1:1 채팅방을 생성합니다.")
    @PostMapping("/room")
    public BaseResponse<ChattingRoomDto.ChattingRes> createRoom(
            @Parameter(description = "상품 ID") @RequestParam Long productIdx,
            @Parameter(description = "구매자 ID") @RequestParam Long buyerId) {

        return chattingRoomService.createChattingRoom(productIdx, buyerId);
    }

    @Operation(summary = "채팅방 확인 또는 생성", description = "상품 ID와 구매자 ID를 기반으로 기존 채팅방이 있는지 확인하고, 없으면 새로 생성합니다.")
    @PostMapping("/rooms/check")
    public BaseResponse<ChattingRoomDto.ChattingRes> getOrCreateChatRoom(
            @Valid @RequestBody ChattingRoomDto.ChattingReq request) {

        return chattingRoomService.createChattingRoom(request.getUserIdx(), request.getProductIdx());
    }

    @Operation(summary = "채팅방 목록 조회", description = "모든 채팅방 목록을 조회합니다.")
    @GetMapping("/rooms")
    public BaseResponse<List<ChattingRoomDto.ChattingRes>> getAllRooms() {
        return chattingRoomService.getAllRooms();
    }

    @Operation(summary = "특정 채팅방 정보 및 메시지 조회", description = "채팅방 ID를 통해 특정 채팅방의 상세 정보를 가져옵니다.")
    @GetMapping("/room/{identifier}")
    public BaseResponse<ChattingRoomDto.ChattingRes> getRoomDetails(
            @Parameter(description = "채팅방 ID") @PathVariable String identifier) {

        return chattingRoomService.getRoomWithMessages(identifier);
    }

    @Operation(summary = "특정 유저의 채팅방 목록 조회", description = "사용자의 ID를 통해 해당 사용자의 채팅방 목록을 조회합니다.")
    @GetMapping("/rooms/{userIdx}")
    public BaseResponse<List<ChattingRoomDto.ChattingRes>> getUserChatRooms(
            @Parameter(description = "사용자 ID") @PathVariable Long userIdx) {

        return chattingRoomService.getUserChatRooms(userIdx);
    }
}
