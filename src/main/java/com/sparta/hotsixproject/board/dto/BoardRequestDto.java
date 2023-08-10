package com.sparta.hotsixproject.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardRequestDto {
    String name;
    String description;
    Integer red;
    Integer green;
    Integer blue;

}
