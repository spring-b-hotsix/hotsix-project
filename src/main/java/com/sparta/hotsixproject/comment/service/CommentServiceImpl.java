package com.sparta.hotsixproject.comment.service;

import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.card.repository.CardRepository;
import com.sparta.hotsixproject.comment.dto.CommentListResponseDto;
import com.sparta.hotsixproject.comment.dto.CommentRequestDto;
import com.sparta.hotsixproject.comment.dto.CommentResponseDto;
import com.sparta.hotsixproject.comment.entity.Comment;
import com.sparta.hotsixproject.comment.repository.CommentRepository;
import com.sparta.hotsixproject.exception.NotFoundException;
import com.sparta.hotsixproject.exception.annotation.CommentCheckPageAndUser;
import com.sparta.hotsixproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;

    // 선택한 카드에 대한 댓글 작성
    @Override
    @Transactional
    @CommentCheckPageAndUser
    public CommentResponseDto createComment (Long boardId, Long sideId, Long cardId,
                                             CommentRequestDto requestDto, User user) {
        Card card = findCard(cardId);
        Comment comment = new Comment(card, requestDto, user);
        return new CommentResponseDto(commentRepository.save(comment));
    }

    // 선택한 카드에 대한 댓글 조회
    @Override
    @Transactional(readOnly = true)
    @CommentCheckPageAndUser
    public CommentListResponseDto getComments (Long boardId, Long sideId, Long cardId) {
        Card card = findCard(cardId);
        return new CommentListResponseDto(commentRepository.findAllByCardOrderByCreatedAtDesc(card)
                .stream().map(CommentResponseDto::new).toList());
    }

    // 선택한 카드에 대한 해당 댓글 수정
    @Override
    @Transactional
    @CommentCheckPageAndUser
    public CommentResponseDto updateComment (Long boardId, Long sideId, Long cardId,
                                             Long commentId, CommentRequestDto requestDto, User user) {
        findComment(commentId).update(requestDto);
        return new CommentResponseDto(findComment(commentId));
    }

    // 선택한 카드에 대한 해당 댓글 삭제
    @Override
    @Transactional
    @CommentCheckPageAndUser
    public void deleteComment (Long boardId, Long sideId, Long cardId,
                               Long commentId, User user) {
        commentRepository.delete(findComment(commentId));
    }

    // id에 따른 댓글 찾기
    @Override
    public Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new NotFoundException("선택한 댓글은 존재하지 않습니다.")
        );
    }

    // id에 따른 카드 찾기
    @Override
    public Card findCard(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() ->
                new NotFoundException("선택한 카드는 존재하지 않습니다.")
        );
    }
}
