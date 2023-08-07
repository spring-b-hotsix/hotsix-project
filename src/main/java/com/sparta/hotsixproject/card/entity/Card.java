package com.sparta.hotsixproject.card.entity;

import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.card.dto.CardRequestDto;
import com.sparta.hotsixproject.cardlabel.entity.CardLabel;
import com.sparta.hotsixproject.common.entity.TimeStamped;
import com.sparta.hotsixproject.side.entity.Side;
import com.sparta.hotsixproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "card", orphanRemoval = true)
    private List<CardLabel> cardLabelList = new ArrayList<>();

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
