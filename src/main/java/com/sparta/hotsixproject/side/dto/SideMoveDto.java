package com.sparta.hotsixproject.side.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SideMoveDto {
    @NotBlank
    private Long boardId;
    @NotBlank
    private Long order;
}
