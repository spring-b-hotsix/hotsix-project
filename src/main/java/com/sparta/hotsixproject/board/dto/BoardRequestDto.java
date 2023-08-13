package com.sparta.hotsixproject.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequestDto {
    String name;
    String description;
    Integer red;
    Integer green;
    Integer blue;

}
