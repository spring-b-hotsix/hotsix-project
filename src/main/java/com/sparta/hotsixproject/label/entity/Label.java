package com.sparta.hotsixproject.label.entity;

import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.cardlabel.entity.CardLabel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String color;

    @ManyToOne
    @JoinColumn(name="board_id", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "label", orphanRemoval = true)
    private List<CardLabel> cardLabelList;

    public Label(Board board, String title, String color) {
        this.board = board;
        this.title = title;
        this.color = color;
    }

    public void update(String title, String color) {
        this.title = title;
        this.color = color;
    }
}
