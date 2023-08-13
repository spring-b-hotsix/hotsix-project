package com.sparta.hotsixproject.label.entity;

import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.cardlabel.entity.CardLabel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private Integer red;
    @Column
    private Integer green;
    @Column
    private Integer blue;
    @ManyToOne
    @JoinColumn(name="board_id", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "label", orphanRemoval = true)
    private List<CardLabel> cardLabelList = new ArrayList<>();

    public Label(Board board, String title, Integer red,Integer green,Integer blue) {
        this.board = board;
        this.title = title;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public void update(String title, Integer red, Integer green, Integer blue) {
        this.title = title;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public void addCardLabel(CardLabel cardLabel) {
        this.cardLabelList.add(cardLabel);
        cardLabel.setLabel(this);
    }
    public void removeCardLabel(CardLabel cardLabel) {
        this.cardLabelList.remove(cardLabel);
        cardLabel.setLabel(null);
    }
}