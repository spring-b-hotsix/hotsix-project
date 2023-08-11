package com.sparta.hotsixproject.notification.service;

import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.card.event.CardUpdateEvent;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.notification.entity.Notification;
import com.sparta.hotsixproject.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sparta.hotsixproject.notification.NotificationTitle.CARD_NAME_UPDATED;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

//    @Transactional
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);

//        ApiResponseDto apiResponseDto = new ApiResponseDto("알림 저장 완료", HttpStatus.CREATED.value());
//        return new ResponseEntity<>(apiResponseDto, HttpStatus.CREATED);
    }
}
