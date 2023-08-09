package com.sparta.hotsixproject.user.dto;

import com.sparta.hotsixproject.user.entity.User;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {
    private Long userId;
    private String nickname;
    private String email;

    public UserInfoResponseDto(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }
}
