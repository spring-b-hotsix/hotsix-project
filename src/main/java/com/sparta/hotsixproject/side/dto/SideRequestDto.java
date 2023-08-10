package com.sparta.hotsixproject.side.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SideRequestDto {
    @NotBlank
    private String name;
}