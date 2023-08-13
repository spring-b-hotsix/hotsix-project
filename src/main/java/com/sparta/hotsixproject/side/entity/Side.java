package com.sparta.hotsixproject.side.entity;

import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.card.entity.Card;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "side")
@NoArgsConstructor
public class Side {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private int position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "side", orphanRemoval = true)
    private List<Card> cardList = new ArrayList<>();


    public Side(String name, int position, Board board) {
        this.name = name;
        this.position = position;
        this.board = board;
    }

    public void updateSideName(String name) {
        this.name = name;
    }

    public void moveSide(Board board, int position) {
        this.board = board;
        this.position = position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
