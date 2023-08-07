package com.sparta.hotsixproject.comment.service;

import com.sparta.hotsixproject.comment.dto.CommentListResponseDto;
import com.sparta.hotsixproject.comment.dto.CommentRequestDto;
import com.sparta.hotsixproject.comment.dto.CommentResponseDto;
import com.sparta.hotsixproject.comment.entity.Comment;
import com.sparta.hotsixproject.user.entity.User;

public interface CommentService {

    /**
     * 댓글 생성
     * @param cardId 카드 번호
     * @param requestDto 댓글 생성 요청정보
     * @param user 댓글 생성 요청자
     * @return 댓글 생성 결과
     */
    CommentResponseDto createComment (Long cardId, CommentRequestDto requestDto, User user);

    /**
     * 선택한 카드에 대한 전체 댓글 목록 조회
     * @return 선택한 게시글에 대한 전체 댓글 목록
     */
    CommentListResponseDto getComments (Long cardId);

    /**
     * 댓글 수정
     * @param commentId 댓글 번호
     * @param requestDto 댓글 수정 요청정보
     * @param user 댓글 수정 요청자
     * @return 댓글 수정 결과
     */
    CommentResponseDto updateComment (Long commentId, CommentRequestDto requestDto, User user);

    /**
     * 댓글 삭제
     * @param commentId 댓글 번호
     * @param user 댓글 삭제 요청자
     */
    void deleteComment (Long commentId, User user);

    /**
     * id로 댓글 찾기
     * @param commentId 댓글 번호
     */
    Comment findComment(Long commentId);
}
