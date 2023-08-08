package com.sparta.hotsixproject.comment.dto;

import com.sparta.hotsixproject.comment.entity.Comment;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto extends ApiResponseDto {
    private String content;
    private String nickname;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {
        this.content = comment.getContent();
        this.nickname = comment.getUser().getNickname();
        this.createdAt = comment.getCreatedAt();
    }
}
