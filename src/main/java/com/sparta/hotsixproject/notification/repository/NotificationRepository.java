package com.sparta.hotsixproject.notification.repository;

import com.sparta.hotsixproject.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByBoard_Id(Long id);
}
