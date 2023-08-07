package com.sparta.hotsixproject.side.entity;

import com.sparta.hotsixproject.board.entity.Board;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table
@NoArgsConstructor
public class Side {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private Long order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;


    public Side(String name, Long order) {
        this.name = name;
        this.order = order;
    }
}
