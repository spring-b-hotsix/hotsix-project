package com.sparta.hotsixproject.side.dto;

import jakarta.validation.constraints.NotBlank;

public class SideRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private Long boardId;
}