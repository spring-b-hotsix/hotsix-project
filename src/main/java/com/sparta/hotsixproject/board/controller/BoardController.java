package com.sparta.hotsixproject.board.controller;

import com.sparta.hotsixproject.board.dto.BoardRequestDto;
import com.sparta.hotsixproject.board.dto.BoardResponseDto;
import com.sparta.hotsixproject.board.dto.InviteBoardRequestDto;
import com.sparta.hotsixproject.board.service.BoardService;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.common.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BoardController {

    private BoardService boardService;

    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    @GetMapping("/boards")
    public ResponseEntity<List<BoardResponseDto>> getBoards(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<BoardResponseDto> boards = new ArrayList<>();
        boards.addAll(boardService.getMyBoards(userDetails.getUser()));
        boards.addAll(boardService.getGuestBoards(userDetails.getUser()));
        return ResponseEntity.status(HttpStatus.OK).body(boards);
    }
    @GetMapping("/myboard")
    public ResponseEntity<List<BoardResponseDto>> getMyBoards(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.getMyBoards(userDetails.getUser()));
    }
    @GetMapping("/guestboard")
    public ResponseEntity<List<BoardResponseDto>> getGuestBoards(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.getGuestBoards(userDetails.getUser()));
    }

    @GetMapping("/boards/{boardId}")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.getBoard(boardId, userDetails.getUser()));
    }

    @PostMapping("/boards")
    public ResponseEntity<ApiResponseDto> createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.createBoard(requestDto, userDetails.getUser()));
    }

    @PutMapping("/boards/{boardId}")
    public ResponseEntity<ApiResponseDto> updateBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.updateBoard(boardId, requestDto, userDetails.getUser()));
    }

    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<ApiResponseDto> deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(boardService.deleteBoard(boardId, userDetails.getUser()));
    }

    @PostMapping("/boards/{boardId}/members")
    public ResponseEntity<ApiResponseDto> inviteBoard(@PathVariable Long boardId, @RequestBody InviteBoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.inviteBoard(boardId,requestDto.getEmail(),userDetails.getUser()));
    }
}
