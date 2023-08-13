package com.sparta.hotsixproject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNicknameRequestDto {
    private String nickname;
    private String password;
}
