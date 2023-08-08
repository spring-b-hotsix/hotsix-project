package com.sparta.hotsixproject.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateNicknameRequestDto {
    private String nickname;
    private String password;
}
