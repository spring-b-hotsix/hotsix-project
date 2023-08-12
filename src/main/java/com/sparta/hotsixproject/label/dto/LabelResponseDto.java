package com.sparta.hotsixproject.label.dto;

import com.sparta.hotsixproject.cardlabel.entity.CardLabel;
import com.sparta.hotsixproject.label.entity.Label;
import lombok.Getter;

@Getter
public class LabelResponseDto {
    private Long labelId;
    private String title;
    private Integer red;
    private Integer green;
    private Integer blue;

    public LabelResponseDto(Label label) {
        this.labelId = label.getId();
        this.title = label.getTitle();
        this.red = label.getRed();
        this.green = label.getGreen();
        this.blue = label.getBlue();
    }

    public LabelResponseDto(CardLabel cardLabel) {
        this.labelId = cardLabel.getLabel().getId();
        this.title = cardLabel.getLabel().getTitle();
        this.red = cardLabel.getLabel().getRed();
        this.green = cardLabel.getLabel().getGreen();
        this.blue = cardLabel.getLabel().getBlue();
    }
}
