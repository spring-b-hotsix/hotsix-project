package com.sparta.hotsixproject.label.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LabelRequestDto {
    private String title;
    private Integer red;
    private Integer green;
    private Integer blue;
}
