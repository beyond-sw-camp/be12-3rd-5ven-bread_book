package com.example.breadbook.Notification;

import com.example.breadbook.Notification.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
