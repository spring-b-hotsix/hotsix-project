package com.sparta.hotsixproject.user.dto;

import lombok.Getter;

@Getter
public class UserInfoDto {
    private String nickname;
    private String email;
    public UserInfoDto(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}
