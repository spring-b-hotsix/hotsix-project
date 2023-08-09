package com.sparta.hotsixproject.boarduser.controller;

import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "board_user")
public class BoardUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "boared_id",nullable = false)
    private Board board;

    public BoardUser(User user, Board board) {
        this.user = user;
        this.board = board;
    }
}
