package com.sparta.hotsixproject.checklist.checklistItem.dto;

import com.sparta.hotsixproject.checklist.checklistItem.entity.ChecklistItem;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import lombok.Getter;

@Getter
public class ChecklistItemResponseDto extends ApiResponseDto {
    private Long itemId;
    private String content;
    private Boolean checked;

    public ChecklistItemResponseDto(ChecklistItem checklistItem) {
        this.itemId = checklistItem.getId();
        this.content = checklistItem.getContent();
        this.checked = checklistItem.getChecked();
    }
}
