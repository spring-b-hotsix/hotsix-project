package com.sparta.hotsixproject.checklist.dto;

import com.sparta.hotsixproject.checklist.checklistItem.dto.ChecklistItemResponseDto;
import com.sparta.hotsixproject.checklist.entity.Checklist;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class ChecklistResponseDto extends ApiResponseDto {
    private Long checklistId;
    private String name;
    private List<ChecklistItemResponseDto> checklistItems;

    public ChecklistResponseDto(Checklist checklist) {
        this.checklistId = checklist.getId();
        this.name = checklist.getName();
        if (!(checklist.getChecklistItems() == null)) {
            this.checklistItems = checklist.getChecklistItems().stream()
                    .map(ChecklistItemResponseDto::new)
                    .toList();
        }
    }
}
