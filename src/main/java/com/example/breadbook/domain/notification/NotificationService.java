package com.example.breadbook.domain.notification;


import com.example.breadbook.domain.notification.model.Notification;
import com.example.breadbook.domain.notification.model.NotificationDto;
import com.example.breadbook.domain.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    
    public void register(NotificationDto.NotificationRegister dto, Member member) {
        Notification notification = notificationRepository.save(dto.toEntity(member));
    }

    @Transactional(readOnly = true)
    public List<NotificationDto.NotificationResponse> list() {
        List<Notification> notificationList = notificationRepository.findAll();

        return notificationList.stream().map(NotificationDto.NotificationResponse::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public NotificationDto.NotificationResponse read(Long notificationIdx) {
        Notification notification = notificationRepository.findById(notificationIdx).orElseThrow();
        return NotificationDto.NotificationResponse.from(notification);
    }
}
