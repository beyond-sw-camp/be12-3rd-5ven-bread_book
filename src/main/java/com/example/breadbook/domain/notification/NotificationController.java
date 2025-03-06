package com.example.breadbook.domain.notification;


import com.example.breadbook.domain.notification.model.NotificationDto;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
@Tag(name = "알림 기능")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/register")
    @Operation(summary = "알림생성", description = "신고가 들어오거나, 쳇팅이 날라오거나, 거래신청이 들어왔을 때 알람이 생성됩니다.")
    public void register(@AuthenticationPrincipal Member member, @AuthenticationPrincipal Product product, @RequestBody NotificationDto.NotificationRegister dto) {
        notificationService.register(dto, member, product);
    }

    @GetMapping("/list")
    @Operation(summary = "알림 리스트 출력", description = "전달받은 모든 알림을 출력하여 보여줍니다.")
    public ResponseEntity<List<NotificationDto.NotificationResponse>> list(Long userIdx) {
        List<NotificationDto.NotificationResponse> res = notificationService.list(userIdx);
        return ResponseEntity.ok(res);
    }
}
