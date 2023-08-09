package com.sparta.hotsixproject.exception.aop;

import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.board.service.BoardServiceImpl;
import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.comment.service.CommentServiceImpl;
import com.sparta.hotsixproject.exception.NotFoundException;
import com.sparta.hotsixproject.side.entity.Side;
import com.sparta.hotsixproject.user.entity.User;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommonPageAndUserCheckAspect {

    private final CommentServiceImpl commentService;
    private final BoardServiceImpl boardService;

    @Autowired
    public CommonPageAndUserCheckAspect(CommentServiceImpl commentService, BoardServiceImpl boardService) {
        this.commentService = commentService;
        this.boardService = boardService;
    }

    // 받은 보드, 사이드 Id가 카드의 DB와 다를 경우 예외 처리
    public void cardCheck(Long boardId, Long sideId, Long cardId) throws NotFoundException {
        Card card = commentService.findCard(cardId);
        Side side = card.getSide();
        Board board = side.getBoard();
        if (boardId != board.getId() || sideId != side.getId()) {
            throw new NotFoundException("해당 페이지를 찾을 수 없습니다.");
        }
    }

    // 보드의 참여자가 접근을 시도할 경우 예외 처리
    public void boardUserCheck(Long boardId, User user) {
        Board board = boardService.findBoard(boardId);
        boardService.checkBoardMember(user, board);
    }
}