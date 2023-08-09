package com.sparta.hotsixproject.side.dto;

import com.sparta.hotsixproject.card.dto.CardResponseDto;
import com.sparta.hotsixproject.side.entity.Side;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class SideResponseDto {
    private Long sideId;
    private String name;
    private int position;
    private Long boardId;
    private List<CardResponseDto> cardList;

    public SideResponseDto(Side side) {
        this.sideId = side.getId();
        this.name = side.getName();
        this.position = side.getPosition();
        this.boardId = side.getBoard().getId();
        this.cardList = side.getCardList().stream()
                .map(CardResponseDto::new)
                .collect(Collectors.toList());
    }
}
