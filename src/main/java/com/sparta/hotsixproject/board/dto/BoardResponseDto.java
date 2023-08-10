package com.sparta.hotsixproject.board.dto;

import com.sparta.hotsixproject.board.entity.Board;
import lombok.Getter;

@Getter
public class BoardResponseDto {
    private Long boardId;
    private String name;
    private String description;
    private Integer red;
    private Integer green;
    private Integer blue;


    public BoardResponseDto(Board board){
        this.boardId = board.getId();
        this.name = board.getName();
        this.description = board.getDescription();
        this.red = board.getColor().getRed();
        this.green = board.getColor().getGreen();
        this.blue = board.getColor().getBlue();
    }
}
