package com.sparta.hotsixproject.checklist.checklistItem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChecklistItemRequestDto {
    @NotBlank
    private String content;
}
