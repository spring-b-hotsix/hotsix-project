package com.sparta.hotsixproject.side.dto;

import com.sparta.hotsixproject.side.entity.Side;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SideResponseDto {
    private Long id;
    private String name;
    private Long order;
    private Long boardId;

    public SideResponseDto(Side side) {
        this.id = side.getId();
        this.name = side.getName();
        this.order = side.getOrder();
        this.boardId = side.getBoard().getId();
    }
}
