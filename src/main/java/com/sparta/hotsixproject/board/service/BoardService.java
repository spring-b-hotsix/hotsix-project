package com.sparta.hotsixproject.board.service;

import com.sparta.hotsixproject.board.dto.BoardRequestDto;
import com.sparta.hotsixproject.board.dto.BoardResponseDto;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.user.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardService {

    List<BoardResponseDto> getMyBoards(User user);
    List<BoardResponseDto> getGuestBoards(User user);

    BoardResponseDto getBoard(Long id, User user);

    @Transactional
    ApiResponseDto createBoard(BoardRequestDto requestDto, User user);

    @Transactional
    ApiResponseDto updateBoard(Long id,BoardRequestDto requestDto, User user);

    @Transactional
    ApiResponseDto deleteBoard(Long id, User user);
    ApiResponseDto inviteBoard(Long id, String email,User user);
}
