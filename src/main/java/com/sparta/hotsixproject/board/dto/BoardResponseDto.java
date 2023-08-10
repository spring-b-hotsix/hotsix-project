package com.sparta.hotsixproject.board.dto;

import com.sparta.hotsixproject.board.entity.Board;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.awt.*;

@Getter
public class BoardResponseDto {
    private Long id;
    private String name;
    private String description;
    private Integer red;
    private Integer green;
    private Integer blue;


    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.name = board.getName();
        this.description = board.getDescription();
        this.red = board.getColor().getRed();
        this.green = board.getColor().getGreen();
        this.blue = board.getColor().getBlue();
    }
}
