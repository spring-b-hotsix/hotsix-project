package com.sparta.hotsixproject.checklist.dto;

import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class ChecklistsResponseDto extends ApiResponseDto {
    private List<ChecklistResponseDto> checklists;

    public ChecklistsResponseDto(List<ChecklistResponseDto> checklists) {
        this.checklists = checklists;
    }
}
