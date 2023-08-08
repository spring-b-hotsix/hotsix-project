package com.sparta.hotsixproject.exception.aop;

import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.board.service.BoardServiceImpl;
import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.comment.dto.CommentRequestDto;
import com.sparta.hotsixproject.comment.entity.Comment;
import com.sparta.hotsixproject.comment.service.CommentServiceImpl;
import com.sparta.hotsixproject.exception.NotFoundException;
import com.sparta.hotsixproject.exception.UnauthorizedException;
import com.sparta.hotsixproject.side.entity.Side;
import com.sparta.hotsixproject.user.entity.User;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommentCheckPageAndUserAspect {
	private final CommentServiceImpl commentService;
	private final BoardServiceImpl boardService;

	@Autowired
	public CommentCheckPageAndUserAspect(CommentServiceImpl commentService, BoardServiceImpl boardService) {
		this.commentService = commentService;
		this.boardService = boardService;
	}

	@Before("@annotation(com.sparta.hotsixproject.exception.annotation.CommentCheckPageAndUser) && args(boardId, sideId, cardId, requestDto, user)")
	public void commentCheckPage(Long boardId, Long sideId, Long cardId,
								 CommentRequestDto requestDto, User user) {
		boardUserCheck(boardId, user);
		cardCheck(boardId, sideId, cardId);
	}

	@Before("@annotation(com.sparta.hotsixproject.exception.annotation.CommentCheckPageAndUser) && args(boardId, sideId, cardId, user)")
	public void commentCheckPage(Long boardId, Long sideId, Long cardId, User user) {
		boardUserCheck(boardId, user);
		cardCheck(boardId, sideId, cardId);
	}

	@Before("@annotation(com.sparta.hotsixproject.exception.annotation.CommentCheckPageAndUser) && args(boardId, sideId, cardId, commentId, requestDto, user)")
	public void commentCheckPage(Long boardId, Long sideId, Long cardId,
								 Long commentId, CommentRequestDto requestDto, User user) {
		boardUserCheck(boardId, user);
		commentCheck(boardId, sideId, cardId, commentId);
		userCheckEdit(commentId, user);
	}

	@Before("@annotation(com.sparta.hotsixproject.exception.annotation.CommentCheckPageAndUser) && args(boardId, sideId, cardId, commentId, user)")
	public void commentCheckPage(Long boardId, Long sideId, Long cardId,
								 Long commentId, User user) {
		boardUserCheck(boardId, user);
		commentCheck(boardId, sideId, cardId, commentId);
		userCheckDelete(cardId, commentId, user);
	}


	// 받은 보드, 사이드 Id가 카드의 DB와 다를 경우 예외 처리
	private void cardCheck(Long boardId, Long sideId, Long cardId) throws NotFoundException {
		Card card = commentService.findCard(cardId);
		Side side = card.getSide();
		Board board = side.getBoard();
		if (boardId != board.getId() || sideId != side.getId()) {
			throw new NotFoundException("해당 페이지를 찾을 수 없습니다.");
		}
	}

	// 받은 카드 Id가 댓글의 DB와 다를 경우 예외 처리
	private void commentCheck(Long boardId, Long sideId, Long cardId, Long commentId) throws NotFoundException {
		cardCheck(boardId, sideId, cardId);
		Comment comment = commentService.findComment(commentId);
		if (cardId != comment.getCard().getId()) {
			throw new NotFoundException("해당 페이지를 찾을 수 없습니다.");
		}
	}

	// 댓글의 작성자가 아닐 경우 예외 처리 (수정은 댓글 작성자만 가능)
	private void userCheckEdit(Long commentId, User user) {
		User commentUser = commentService.findComment(commentId).getUser();
		if (commentUser.getId() != user.getId()) {
			throw new UnauthorizedException("해당 댓글에 접근할 수 없습니다.");
		}
	}

	// 카드 또는 댓글의 작성자가 아닐 경우 예외 처리(삭제는 댓글의 작성자와 수정자 모두 가능)
	private void userCheckDelete(Long cardId, Long commentId, User user) {
		User commentUser = commentService.findComment(commentId).getUser();
		User cardUser = commentService.findCard(cardId).getUser();
		if (commentUser.getId() != user.getId() && cardUser.getId() != user.getId()) {
			throw new UnauthorizedException("해당 댓글에 접근할 수 없습니다.");
		}
	}

	// 보드의 참여자가 접근을 시도할 경우 예외 처리
	private void boardUserCheck(Long boardId, User user) {
		Board board = boardService.findBoard(boardId);
		boardService.checkBoardMember(user, board);
	}
}