package com.sparta.hotsixproject.side.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SideMoveDto {
    @NotBlank
    private Long selectBoardId;
    @NotBlank
    private int selectIndex;
}
