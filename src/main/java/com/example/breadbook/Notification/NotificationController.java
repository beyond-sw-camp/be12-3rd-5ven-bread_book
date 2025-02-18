package com.example.breadbook.Notification;


import com.example.breadbook.Notification.model.NotificationDto;
import com.example.breadbook.domain.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NotificationController {
    private final NotificationService notificationService;
    
    @PostMapping("/register")
    public void register(@AuthenticationPrincipal Member member, @RequestBody NotificationDto.NotificationRegister dto) {
        notificationService.register(dto, member);
    }

    @GetMapping("/list")
    public ResponseEntity<List<NotificationDto.NotificationResponse>> list() {
        List<NotificationDto.NotificationResponse> res = notificationService.list();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{notificationIdx")
    public ResponseEntity<NotificationDto.NotificationResponse> get(@PathVariable Long notificationIdx) {
        NotificationDto.NotificationResponse response = notificationService.read(notificationIdx);
        return ResponseEntity.ok(response);
    }
}
