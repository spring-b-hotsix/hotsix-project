package com.sparta.hotsixproject.notification.dto;

import com.sparta.hotsixproject.notification.NotificationTitle;
import com.sparta.hotsixproject.notification.entity.Notification;
import lombok.Getter;

import java.time.Duration;

@Getter
public class NotificationResponseDto {
    private Long notificationId;
    private NotificationTitle title;
    private String detail;
    private Long editedTime;
    private String editorName;

    public NotificationResponseDto(Notification notification) {
        this.notificationId = notification.getId();
        this.title = notification.getTitle();
        this.detail = notification.getDetail();
        this.editedTime = Duration.parse(notification.getTimeSinceModified()).toMinutes();
        this.editorName = notification.getEditor().getNickname();
    }
}
