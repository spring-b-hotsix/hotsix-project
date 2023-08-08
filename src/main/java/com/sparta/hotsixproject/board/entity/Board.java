package com.sparta.hotsixproject.board.entity;

import com.sparta.hotsixproject.board.dto.BoardRequestDto;
import com.sparta.hotsixproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "boards")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable=false)
    private String name;

    @Column
    private String description;

    @Column
    private Color color;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;


    public Board(String name, String description, Color color,User user){
        this.name = name;
        this.description = description;
        this.color = color;
        this.user = user;
    }

    public void update(BoardRequestDto request){
        this.name = request.getName();
        this.description = request.getDescription();
        this.color = request.getColor();
    }




}
