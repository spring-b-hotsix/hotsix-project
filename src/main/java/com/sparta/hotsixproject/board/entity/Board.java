package com.sparta.hotsixproject.board.entity;

import com.sparta.hotsixproject.card.entity.Card;
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
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Side> sideList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User owner;
    
    // user와 board 다대다 관계 필요

}
