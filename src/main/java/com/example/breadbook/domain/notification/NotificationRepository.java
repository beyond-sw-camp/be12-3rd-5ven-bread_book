package com.example.breadbook.domain.notification;

import com.example.breadbook.domain.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByMemberIdx(Long idx);
}
