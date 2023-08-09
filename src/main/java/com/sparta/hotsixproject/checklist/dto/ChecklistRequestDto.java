package com.sparta.hotsixproject.checklist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ChecklistRequestDto {
    @NotBlank
    private String name;
}
