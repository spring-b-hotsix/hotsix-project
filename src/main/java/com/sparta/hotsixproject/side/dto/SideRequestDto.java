package com.sparta.hotsixproject.side.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SideRequestDto {
    @NotBlank
    private String name;
}