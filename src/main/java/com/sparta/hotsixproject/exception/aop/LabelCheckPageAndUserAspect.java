package com.sparta.hotsixproject.exception.aop;

import com.sparta.hotsixproject.exception.NotFoundException;
import com.sparta.hotsixproject.label.dto.LabelRequestDto;
import com.sparta.hotsixproject.label.entity.Label;
import com.sparta.hotsixproject.label.service.LabelService;
import com.sparta.hotsixproject.user.entity.User;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LabelCheckPageAndUserAspect {
    private final CommonPageAndUserCheckAspect commonPageAndUserCheckAspect;
    private final LabelService labelService;

    @Autowired
    public LabelCheckPageAndUserAspect(CommonPageAndUserCheckAspect commonPageAndUserCheckAspect, LabelService labelService) {
        this.commonPageAndUserCheckAspect = commonPageAndUserCheckAspect;
        this.labelService = labelService;
    }

    @Before("@annotation(com.sparta.hotsixproject.exception.annotation.LabelCheckPageAndUser) && args(boardId, requestDto, user)")
    public void labelCheckPageAndUser(Long boardId, LabelRequestDto requestDto, User user) {
        commonPageAndUserCheckAspect.boardUserCheck(boardId, user);
    }

    @Before("@annotation(com.sparta.hotsixproject.exception.annotation.LabelCheckPageAndUser) && args(boardId, user)")
    public void labelCheckPageAndUser(Long boardId, User user) {
        commonPageAndUserCheckAspect.boardUserCheck(boardId, user);
    }

    @Before("@annotation(com.sparta.hotsixproject.exception.annotation.LabelCheckPageAndUser) && args(boardId, labelId, requestDto, user)")
    public void labelCheckPageAndUser(Long boardId, Long labelId, LabelRequestDto requestDto, User user) {
        commonPageAndUserCheckAspect.boardUserCheck(boardId, user);
        labelCheck(boardId, labelId);
    }

    @Before("@annotation(com.sparta.hotsixproject.exception.annotation.LabelCheckPageAndUser) && args(boardId, labelId, user)")
    public void labelCheckPageAndUser(Long boardId, Long labelId, User user) {
        commonPageAndUserCheckAspect.boardUserCheck(boardId, user);
        labelCheck(boardId, labelId);
    }

    @Before("@annotation(com.sparta.hotsixproject.exception.annotation.LabelCheckPageAndUser) " +
            "&& args(boardId, sideId, cardId, labelId, user)")
    public void labelCheckPageAndUser(Long boardId, Long sideId, Long cardId, Long labelId, User user) {
        commonPageAndUserCheckAspect.boardUserCheck(boardId, user);
        commonPageAndUserCheckAspect.cardCheck(boardId, sideId, cardId);
        labelCheck(boardId, labelId);
    }

    @Before("@annotation(com.sparta.hotsixproject.exception.annotation.LabelCheckPageAndUser) " +
            "&& args(boardId, sideId, cardId, user)")
    public void labelCheckPageAndUser(Long boardId, Long sideId, Long cardId, User user) {
        commonPageAndUserCheckAspect.boardUserCheck(boardId, user);
        commonPageAndUserCheckAspect.cardCheck(boardId, sideId, cardId);
    }

    // 받은 보드 Id가 라벨의 DB와 다를 경우 예외 처리
    private void labelCheck(Long boardId, Long labelId) throws NotFoundException {
        Label label = labelService.findLabel(labelId);
        if (boardId != label.getBoard().getId()) {
            throw new NotFoundException("해당 페이지를 찾을 수 없습니다.");
        }
    }
}
