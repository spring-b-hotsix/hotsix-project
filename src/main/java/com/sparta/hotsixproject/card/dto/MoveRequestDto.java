package com.sparta.hotsixproject.card.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MoveRequestDto {
    private String sideName;
    private int position;
}
