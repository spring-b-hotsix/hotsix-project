package com.sparta.hotsixproject.notification.repository;

import com.sparta.hotsixproject.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
