package com.sparta.hotsixproject.notification.service;

import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.card.event.CardUpdateEvent;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.notification.dto.NotificationResponseDto;
import com.sparta.hotsixproject.notification.entity.Notification;
import com.sparta.hotsixproject.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.sparta.hotsixproject.notification.NotificationTitle.CARD_NAME_UPDATED;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Transactional
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public List<NotificationResponseDto> getNotifications() {
        return notificationRepository.findAll().stream().map(NotificationResponseDto::new).toList();
    }
}
