package com.example.breadbook.domain.notification;


import com.example.breadbook.domain.notification.model.Notification;
import com.example.breadbook.domain.notification.model.NotificationDto;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    
    public void register(NotificationDto.NotificationRegister dto, Member member, Product product) {
        Notification notification = notificationRepository.save(dto.toEntity(member, product));
    }

    @Transactional(readOnly = true)
    public List<NotificationDto.NotificationResponse> list(Long userIdx) {
        List<Notification> notificationList = notificationRepository.findByMemberIdx(userIdx);

        return notificationList.stream().map(NotificationDto.NotificationResponse::from).collect(Collectors.toList());
    }
}
