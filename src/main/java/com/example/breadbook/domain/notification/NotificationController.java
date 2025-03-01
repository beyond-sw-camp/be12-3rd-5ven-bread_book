package com.example.breadbook.domain.notification;


import com.example.breadbook.domain.notification.model.NotificationDto;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
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
    public void register(@AuthenticationPrincipal Member member, @AuthenticationPrincipal Product product, @RequestBody NotificationDto.NotificationRegister dto) {
        notificationService.register(dto, member, product);
    }

    @GetMapping("/list")
    public ResponseEntity<List<NotificationDto.NotificationResponse>> list() {
        List<NotificationDto.NotificationResponse> res = notificationService.list();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{notificationIdx}")
    public ResponseEntity<NotificationDto.NotificationResponse> get(@PathVariable Long notificationIdx) {
        NotificationDto.NotificationResponse response = notificationService.read(notificationIdx);
        return ResponseEntity.ok(response);
    }
}
