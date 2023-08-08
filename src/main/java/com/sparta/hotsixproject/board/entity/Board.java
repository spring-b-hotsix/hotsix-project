package com.sparta.hotsixproject.board.entity;

import com.sparta.hotsixproject.board.dto.BoardRequestDto;
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

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<BoardUser> boardUsers;

    public Board(String name, String description, User user,Integer red, Integer green, Integer blue){
        this.name = name;
        this.description = description;
        this.color = new Color(red,green,blue);
        this.user = user;
    }

    public void update(BoardRequestDto request){
        this.name = request.getName();
        this.description = request.getDescription();
        this.color = new Color(request.getRed(),request.getGreen(),request.getBlue());
    }




}
