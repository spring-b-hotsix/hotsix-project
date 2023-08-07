package com.sparta.hotsixproject.card.dto;

import com.sparta.hotsixproject.card.entity.Card;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CardResponseDto {
    private Long id;
    private String name;
    private String description;
    private String color;
    private int position;
//    private LocalDateTime createdAt;
//    private LocalDateTime modifiedAt;

    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.name = card.getName();
        this.description = card.getDescription();
        this.color = card.getColor();
        this.position = card.getPosition();

    }
}
