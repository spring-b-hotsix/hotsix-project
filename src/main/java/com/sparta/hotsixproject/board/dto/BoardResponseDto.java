package com.sparta.hotsixproject.board.dto;

import com.sparta.hotsixproject.board.entity.Board;
import jakarta.persistence.Column;
import lombok.Getter;

import java.awt.*;

@Getter
public class BoardResponseDto {
    private Long boardId;
    private String name;
    @Column
    private String description;
    @Column
    private Color color;

    public BoardResponseDto(Board board){
        this.boardId = board.getId();
        this.name = board.getName();
        this.description = board.getDescription();
        this.color = board.getColor();
    }

}
