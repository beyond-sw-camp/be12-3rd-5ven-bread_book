package com.example.breadbook.domain.notification;

import com.example.breadbook.domain.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
