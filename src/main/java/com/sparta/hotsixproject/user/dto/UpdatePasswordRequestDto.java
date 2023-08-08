package com.sparta.hotsixproject.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdatePasswordRequestDto {
    private String password;
    private String newPassword;
}
