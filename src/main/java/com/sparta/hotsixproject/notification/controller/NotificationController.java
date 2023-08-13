package com.sparta.hotsixproject.notification.controller;

import com.sparta.hotsixproject.common.security.UserDetailsImpl;
import com.sparta.hotsixproject.notification.dto.NotificationResponseDto;
import com.sparta.hotsixproject.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/notifications/{boardId}")
    @Operation(summary = "알림 전체 조회")
    public List<NotificationResponseDto> getNotifications(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return notificationService.getNotifications(boardId, userDetails.getUser());
    }
}
