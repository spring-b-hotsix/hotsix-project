package com.sparta.hotsixproject.notification.controller;

import com.sparta.hotsixproject.notification.dto.NotificationResponseDto;
import com.sparta.hotsixproject.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/notifications")
    @Operation(summary = "알림 전체 조회")
    public List<NotificationResponseDto> getNotifications() {
        return notificationService.getNotifications();
    }
}
