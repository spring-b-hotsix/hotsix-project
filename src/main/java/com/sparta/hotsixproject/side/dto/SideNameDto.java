package com.sparta.hotsixproject.side.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SideNameDto {
    @NotBlank
    private String name;
}
