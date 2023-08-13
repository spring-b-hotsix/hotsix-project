package com.sparta.hotsixproject.notification.service;

import com.sparta.hotsixproject.boarduser.entity.BoardUser;
import com.sparta.hotsixproject.boarduser.repository.BoardUserRepository;
import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.card.event.CardUpdateEvent;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.notification.dto.NotificationResponseDto;
import com.sparta.hotsixproject.notification.entity.Notification;
import com.sparta.hotsixproject.notification.repository.NotificationRepository;
import com.sparta.hotsixproject.user.entity.User;
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
    private final BoardUserRepository boardUserRepository;

    @Transactional
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public List<NotificationResponseDto> getNotifications(Long boardId, User user) {
        if (boardUserRepository.existsByBoard_IdAndUser_Id(boardId, user.getId())) {
            return notificationRepository.findByBoard_Id(boardId).stream().map(NotificationResponseDto::new).toList();
        }
        else {
            return null;
        }
    }
}
