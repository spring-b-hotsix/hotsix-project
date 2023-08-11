package com.sparta.hotsixproject.board.dto;

import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.comment.dto.CommentResponseDto;
import com.sparta.hotsixproject.label.dto.LabelResponseDto;
import com.sparta.hotsixproject.label.entity.Label;
import com.sparta.hotsixproject.side.dto.SideResponseDto;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;

@Getter
public class BoardResponseDto {
    private Long boardId;
    private String name;
    private String description;
    private Integer red;
    private Integer green;
    private Integer blue;
    private List<SideResponseDto> sideList;
    private List<LabelResponseDto> labelList;


    public BoardResponseDto(Board board){
        this.boardId = board.getId();
        this.name = board.getName();
        this.description = board.getDescription();
        this.red = board.getColor().getRed();
        this.green = board.getColor().getGreen();
        this.blue = board.getColor().getBlue();
        this.sideList = board.getSideList().stream()
                .map(SideResponseDto::new)
                .sorted(Comparator.comparing(SideResponseDto::getPosition)) // position순
                .toList();
        this.labelList = board.getLabelList().stream()
                .map(LabelResponseDto::new)
                .toList();
    }
}
