package com.sparta.hotsixproject.user.dto;

import lombok.Getter;

@Getter
public class UserInfoDto {
    private String nickname;
    private String email;
    private String imageUrl;
    public UserInfoDto(String nickname, String email,String imageUrl) {
        this.nickname = nickname;
        this.email = email;
        this.imageUrl=imageUrl;
    }
}
