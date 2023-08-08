package com.sparta.hotsixproject.comment.dto;

import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class CommentListResponseDto extends ApiResponseDto {
    private List<CommentResponseDto> commentList;

    public CommentListResponseDto(List<CommentResponseDto> commentList) {
        this.commentList = commentList;
    }
}
