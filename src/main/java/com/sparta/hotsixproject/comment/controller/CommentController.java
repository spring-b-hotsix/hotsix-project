package com.sparta.hotsixproject.comment.controller;

import com.sparta.hotsixproject.comment.dto.CommentListResponseDto;
import com.sparta.hotsixproject.comment.dto.CommentRequestDto;
import com.sparta.hotsixproject.comment.dto.CommentResponseDto;
import com.sparta.hotsixproject.comment.service.CommentService;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.common.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
@Tag(name = "댓글 관련 API", description = "보드 댓글 관련 API 입니다.")
public class CommentController {
    private final CommentService commentService;

    // 선택한 카드에 대한 댓글 작성
    @PostMapping("/{boardId}/sides/{sideId}/cards/{cardId}/comments")
    @Operation(summary = "카드 댓글 작성", description = "Dto로 정보를 받아와 해당 카드에 댓글을 생성합니다.")
    public ResponseEntity<ApiResponseDto> createComment(
            @PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId,
            @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.createComment(boardId, sideId, cardId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("댓글이 생성되었습니다.", HttpStatus.CREATED.value()));
    }

    // 선택한 카드에 대한 댓글 조회
    @GetMapping("/{boardId}/sides/{sideId}/cards/{cardId}/comments")
    @Operation(summary = "카드 댓글 조회", description = "해당 카드에 대한 모든 댓글을 조회합니다.")
    public ResponseEntity<ApiResponseDto> getComments(
            @PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId) {
        CommentListResponseDto responseDto = commentService.getComments(boardId, sideId, cardId);
        return ResponseEntity.ok().body(responseDto);
    }

    // 선택한 카드에 대한 해당 댓글 수정
    @PutMapping("/{boardId}/sides/{sideId}/cards/{cardId}/comments/{commentId}")
    @Operation(summary = "카드 댓글 수정", description = "Dto로 정보를 받아와 해당 댓글을 수정합니다.")
    public ResponseEntity<ApiResponseDto> updateComment(
            @PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId, @PathVariable Long commentId,
            @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.updateComment(boardId, sideId, cardId, commentId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(responseDto);
    }

    // 선택한 카드에 대한 해당 댓글 삭제
    @DeleteMapping("/{boardId}/sides/{sideId}/cards/{cardId}/comments/{commentId}")
    @Operation(summary = "카드 댓글 삭제", description = "해당 카드에 댓글을 삭제합니다.")
    public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable Long boardId, @PathVariable Long sideId, @PathVariable Long cardId, @PathVariable Long commentId,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(boardId, sideId, cardId, commentId, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("댓글이 삭제되었습니다.", HttpStatus.OK.value()));
    }
}
