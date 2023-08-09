package com.sparta.hotsixproject.checklist.checklistItem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ChecklistItemRequestDto {
    @NotBlank
    private String content;
}
