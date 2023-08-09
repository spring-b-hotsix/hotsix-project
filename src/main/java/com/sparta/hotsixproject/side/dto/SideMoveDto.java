package com.sparta.hotsixproject.side.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SideMoveDto {
    @NotBlank
    private Long selectBoardId;
    @NotBlank
    private int selectIndex;
}
