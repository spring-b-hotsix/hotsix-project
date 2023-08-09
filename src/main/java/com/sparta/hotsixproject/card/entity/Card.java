package com.sparta.hotsixproject.card.entity;


import com.sparta.hotsixproject.attachment.entity.Attachment;
import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.card.dto.CardRequestDto;
import com.sparta.hotsixproject.cardlabel.entity.CardLabel;
import com.sparta.hotsixproject.carduser.entity.CardUser;
import com.sparta.hotsixproject.checklist.entity.Checklist;
import com.sparta.hotsixproject.comment.entity.Comment;
import com.sparta.hotsixproject.common.entity.TimeStamped;
import com.sparta.hotsixproject.side.entity.Side;
import com.sparta.hotsixproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private String color;

    @Column
    private int position;

    @Column
    private LocalDateTime due;

    @OneToMany(mappedBy = "card", orphanRemoval = true)
    private List<CardLabel> cardLabelList = new ArrayList<>();

    @OneToMany(mappedBy = "card", orphanRemoval = true)
    private List<Attachment> attachmentList = new ArrayList<>();

    @OneToMany(mappedBy = "card", orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "card", orphanRemoval = true)
    private List<CardUser> cardUserList = new ArrayList<>();

    @OneToMany(mappedBy = "card", orphanRemoval = true)
    private List<Checklist> checklistList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Side side;

    public Card(String name, int position, User user, Side side) {
        this.name = name;
        this.position = position;
        this.user = user;
        this.side = side;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateDesc(String description) {
        this.description = description;
    }

    public void updateColor(String color) {
        this.color = color;
    }

    public void updateDue(LocalDateTime due) {
        this.due = due;
    }

    public void deleteDue() {
        this.due = null;
    }

    public void moveCard(Side side, int position) {
        this.side = side;
        this.position = position;
    }

    public void moveDown() {
        this.position = position + 1;
    }

    public void moveUp() {
        this.position = position -1;
    }
}
