package com.sparta.hotsixproject.board.dto;

import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.board.entity.BoardUser;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private Long userId;
    private String nickname;
    private String email;

    public MemberResponseDto(BoardUser boardUser) {
        this.userId = boardUser.getUser().getId();
        this.nickname = boardUser.getUser().getNickname();
        this.email = boardUser.getUser().getEmail();
    }
}
