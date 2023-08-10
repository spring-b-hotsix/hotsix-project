package com.sparta.hotsixproject.exception.aop;

import com.sparta.hotsixproject.checklist.checklistItem.dto.ChecklistItemRequestDto;
import com.sparta.hotsixproject.checklist.checklistItem.entity.ChecklistItem;
import com.sparta.hotsixproject.checklist.dto.ChecklistRequestDto;
import com.sparta.hotsixproject.checklist.entity.Checklist;
import com.sparta.hotsixproject.checklist.service.ChecklistServiceImpl;
import com.sparta.hotsixproject.exception.NotFoundException;
import com.sparta.hotsixproject.user.entity.User;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ChecklistCheckPageAndUserAspect {
    private final CommonPageAndUserCheckAspect commonPageAndUserCheckAspect;
	private final ChecklistServiceImpl checklistService;

    @Autowired
    public ChecklistCheckPageAndUserAspect(CommonPageAndUserCheckAspect commonPageAndUserCheckAspect, ChecklistServiceImpl checklistService) {
        this.commonPageAndUserCheckAspect = commonPageAndUserCheckAspect;
        this.checklistService = checklistService;
    }

    @Before("@annotation(com.sparta.hotsixproject.exception.annotation.ChecklistCheckPageAndUser) " +
            "&& args(boardId, sideId, cardId, requestDto, user)")
    public void checklistCheckPageAndUser(Long boardId, Long sideId, Long cardId,
                                          ChecklistRequestDto requestDto, User user) {
        commonPageAndUserCheckAspect.boardUserCheck(boardId, user);
        commonPageAndUserCheckAspect.cardCheck(boardId, sideId, cardId);
    }

    @Before("@annotation(com.sparta.hotsixproject.exception.annotation.ChecklistCheckPageAndUser) " +
            "&& args(boardId, sideId, cardId, user)")
    public void checklistCheckPageAndUser(Long boardId, Long sideId, Long cardId, User user) {
        commonPageAndUserCheckAspect.boardUserCheck(boardId, user);
        commonPageAndUserCheckAspect.cardCheck(boardId, sideId, cardId);
    }

    @Before("@annotation(com.sparta.hotsixproject.exception.annotation.ChecklistCheckPageAndUser) " +
            "&& args(boardId, sideId, cardId, checklistId, requestDto, user)")
    public void checklistCheckPageAndUser(Long boardId, Long sideId, Long cardId, Long checklistId,
                                          ChecklistRequestDto requestDto, User user) {
        commonPageAndUserCheckAspect.boardUserCheck(boardId, user);
        checklistCheck(boardId, sideId, cardId, checklistId);
    }

    @Before("@annotation(com.sparta.hotsixproject.exception.annotation.ChecklistCheckPageAndUser) " +
            "&& args(boardId, sideId, cardId, checklistId, user)")
    public void checklistCheckPageAndUser(Long boardId, Long sideId, Long cardId, Long checklistId,
                                          User user) {
        checklistCheck(boardId, sideId, cardId, checklistId);
        commonPageAndUserCheckAspect.boardUserCheck(boardId, user);
    }

    @Before("@annotation(com.sparta.hotsixproject.exception.annotation.ChecklistItemCheckPageAndUser) " +
            "&& args(boardId, sideId, cardId, checklistId, requestDto, user)")
    public void checklistItemCheckPageAndUser(Long boardId, Long sideId, Long cardId, Long checklistId,
                                              ChecklistItemRequestDto requestDto, User user) {
        commonPageAndUserCheckAspect.boardUserCheck(boardId, user);
        checklistCheck(boardId, sideId, cardId, checklistId);
    }

    @Before("@annotation(com.sparta.hotsixproject.exception.annotation.ChecklistItemCheckPageAndUser) " +
            "&& args(boardId, sideId, cardId, checklistId, itemId, user)")
    public void checklistItemCheckPageAndUser(Long boardId, Long sideId, Long cardId, Long checklistId,
                                              Long itemId, User user) {
        commonPageAndUserCheckAspect.boardUserCheck(boardId, user);
        checklistItemCheck(boardId, sideId, cardId, checklistId, itemId);
    }

    @Before("@annotation(com.sparta.hotsixproject.exception.annotation.ChecklistItemCheckPageAndUser) " +
            "&& args(boardId, sideId, cardId, checklistId, itemId, requestDto, user)")
    public void checklistItemCheckPageAndUser(Long boardId, Long sideId, Long cardId, Long checklistId,
                                              Long itemId, ChecklistItemRequestDto requestDto, User user) {
        commonPageAndUserCheckAspect.boardUserCheck(boardId, user);
        checklistItemCheck(boardId, sideId, cardId, checklistId, itemId);
    }

    // 받은 카드 Id가 체크리스트의 DB와 다를 경우 예외 처리
    private void checklistCheck(Long boardId, Long sideId, Long cardId, Long checklistId) throws NotFoundException {
        commonPageAndUserCheckAspect.cardCheck(boardId, sideId, cardId);
        Checklist checklist = checklistService.findChecklist(checklistId);
        if (cardId != checklist.getCard().getId()) {
            throw new NotFoundException("해당 페이지를 찾을 수 없습니다.");
        }
    }

    // 받은 체크리스트 Id가 아이템의 DB와 다를 경우 예외 처리
    private void checklistItemCheck(Long boardId, Long sideId, Long cardId, Long checklistId, Long itemId) throws NotFoundException {
        checklistCheck(boardId, sideId, cardId, checklistId);
        ChecklistItem checklistItem = checklistService.findChecklistItem(itemId);
        if (checklistId != checklistItem.getChecklist().getId()) {
            throw new NotFoundException("해당 페이지를 찾을 수 없습니다.");
        }
    }
}