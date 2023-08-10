package com.sparta.hotsixproject.board.entity;

import com.sparta.hotsixproject.board.dto.BoardRequestDto;
import com.sparta.hotsixproject.boarduser.controller.BoardUser;
import com.sparta.hotsixproject.label.entity.Label;
import com.sparta.hotsixproject.side.entity.Side;
import com.sparta.hotsixproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "boards")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column
    private String description;

    @Column
    private Color color;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<BoardUser> boardUsers;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Side> sideList;

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Label> labelList;

    public Board(String name, String description, User user,Integer red, Integer green, Integer blue){
        this.name = name;
        this.description = description;
        this.color = new Color(red,green,blue);
        this.user = user;
    }
    public Board(String name, String description, User user,Color color){
        this.name = name;
        this.description = description;
        this.color = color;
        this.user = user;
    }

    public void update(BoardRequestDto request){
        this.name = request.getName();
        this.description = request.getDescription();
        this.color = new Color(request.getRed(),request.getGreen(),request.getBlue());
    }
}
