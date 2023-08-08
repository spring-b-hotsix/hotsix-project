package com.sparta.hotsixproject.checklist.checklistItem.dto;

import com.sparta.hotsixproject.checklist.checklistItem.entity.ChecklistItem;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import lombok.Getter;

@Getter
public class ChecklistItemResponseDto extends ApiResponseDto {
    private String content;

    public ChecklistItemResponseDto(ChecklistItem checklistItem) {
        this.content = checklistItem.getContent();
    }
}
