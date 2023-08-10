package com.sparta.hotsixproject.label.dto;

import com.sparta.hotsixproject.cardlabel.entity.CardLabel;
import com.sparta.hotsixproject.label.entity.Label;
import lombok.Getter;

@Getter
public class LabelResponseDto {
    private Long labelId;
    private String title;
    private String color;

    public LabelResponseDto(Label label) {
        this.labelId = label.getId();
        this.title = label.getTitle();
        this.color = label.getColor();
    }

    public LabelResponseDto(CardLabel cardLabel) {
        this.labelId = cardLabel.getLabel().getId();
        this.title = cardLabel.getLabel().getTitle();
        this.color = cardLabel.getLabel().getColor();
    }
}
