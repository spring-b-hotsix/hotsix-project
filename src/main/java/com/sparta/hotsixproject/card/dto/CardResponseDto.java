package com.sparta.hotsixproject.card.dto;

import com.sparta.hotsixproject.attachment.dto.AttachmentResponseDto;
import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.checklist.dto.ChecklistResponseDto;
import com.sparta.hotsixproject.checklist.dto.ChecklistsResponseDto;
import com.sparta.hotsixproject.comment.dto.CommentResponseDto;
import com.sparta.hotsixproject.label.dto.LabelResponseDto;
import com.sparta.hotsixproject.user.dto.UserInfoDto;
import com.sparta.hotsixproject.user.dto.UserInfoResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CardResponseDto {
    private Long cardId;
    private String name;
    private String description;
    private String color;
    private int position;
    private LocalDateTime due;
    private Boolean overdue;
    private List<UserInfoResponseDto> userList;
    private List<LabelResponseDto> labelList;
    private List<AttachmentResponseDto> attachmentList;
    private List<CommentResponseDto> commentList;
    private List<ChecklistResponseDto> checklistList;
    private String sideName;

    public CardResponseDto(Card card) {
        this.cardId = card.getId();
        this.name = card.getName();
        this.description = card.getDescription();
        this.color = card.getColor();
        this.position = card.getPosition();
        this.due = card.getDue();
        this.overdue = due != null && due.isBefore(LocalDateTime.now());
        this.userList = card.getCardUserList().stream().map((cardUser) -> new UserInfoResponseDto(cardUser.getUser())).toList();
        this.labelList = card.getCardLabelList().stream().map((cardLabel) -> new LabelResponseDto(cardLabel.getLabel())).toList();
        this.attachmentList = card.getAttachmentList().stream().map(AttachmentResponseDto::new).toList();
        this.commentList = card.getCommentList().stream().map(CommentResponseDto::new).toList();
        this.checklistList = card.getChecklistList().stream().map(ChecklistResponseDto::new).toList();
        this.sideName = card.getSide().getName();
    }
}
