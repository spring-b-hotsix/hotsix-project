package com.sparta.hotsixproject.user.dto;

import lombok.Getter;

@Getter
public class UserInfoDto {
    private String nickname;

    public UserInfoDto(String nickname) {
        this.nickname = nickname;
    }
}
