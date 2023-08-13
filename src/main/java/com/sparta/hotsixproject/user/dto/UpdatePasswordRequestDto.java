package com.sparta.hotsixproject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordRequestDto {
    private String password;
    private String newPassword;
}
