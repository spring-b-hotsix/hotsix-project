package com.sparta.hotsixproject.comment.controller;

import com.sparta.hotsixproject.comment.dto.CommentListResponseDto;
import com.sparta.hotsixproject.comment.dto.CommentRequestDto;
import com.sparta.hotsixproject.comment.dto.CommentResponseDto;
import com.sparta.hotsixproject.comment.service.CommentService;
import com.sparta.hotsixproject.common.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class CommentController {
    private final CommentService commentService;

    // 선택한 카드에 대한 댓글 작성
    @PostMapping("/{boardId}/sides/{sideId}/cards/{cardId}/comments")
    public ResponseEntity<ApiResponseDto> createComment(
            @PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId,
            @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.createComment(boardId, sideId, cardId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.CREATED.value(), "댓글이 생성되었습니다."));
    }

    // 선택한 카드에 대한 댓글 조회
    @GetMapping("/{boardId}/sides/{sideId}/cards/{cardId}/comments")
    public ResponseEntity<ApiResponseDto> getComments(
            @PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentListResponseDto responseDto = commentService.getComments(boardId, sideId, cardId, userDetails.getUser());
        return ResponseEntity.ok().body(responseDto);
    }

    // 선택한 카드에 대한 해당 댓글 수정
    @PutMapping("/{boardId}/sides/{sideId}/cards/{cardId}/comments/{commentId}")
    public ResponseEntity<ApiResponseDto> updateComment(
            @PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId, @PathVariable Long commentId,
            @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(responseDto);
    }

    // 선택한 카드에 대한 해당 댓글 삭제
    @DeleteMapping("/{boardId}/sides/{sideId}/cards/{cardId}/comments/{commentId}")
    public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(commentId, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto(HttpStatus.OK.value(), "댓글이 삭제되었습니다."));
    }
}
