package com.sparta.hotsixproject.comment.dto;

import com.sparta.hotsixproject.comment.entity.Comment;
import com.sparta.hotsixproject.common.dto.ApiResponseDto;

import java.time.LocalDateTime;

public class CommentResponseDto extends ApiResponseDto  {
    private String content;
    private String nickname;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {
        this.content = comment.getContent();
        this.nickname = comment.getCard().getNickname();
        this.createdAt = comment.getCreatedAt();
    }
}
