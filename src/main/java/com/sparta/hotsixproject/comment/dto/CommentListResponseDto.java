package com.sparta.hotsixproject.comment.dto;

import com.sparta.hotsixproject.common.dto.ApiResponseDto;

import java.util.List;

public class CommentListResponseDto extends ApiResponseDto {
    private List<CommentResponseDto> commentList;

    public CommentListResponseDto(List<CommentResponseDto> commentList) {
        this.commentList = commentList;
    }
}
