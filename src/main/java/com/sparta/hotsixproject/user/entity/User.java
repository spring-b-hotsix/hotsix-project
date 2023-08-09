package com.sparta.hotsixproject.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.user.UserRoleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"boardList", "cardList"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Card> cardList = new ArrayList<>();

    @Column
    private Long kakaoId;

    @Column
    private Long googleId;

    public User(String nickname, String password, String email, UserRoleEnum role) {
        this.nickname=nickname;
        this.password=password;
        this.email=email;
        this.role=role;
    }

    public User(String nickname, String encodedPassword, String email, UserRoleEnum userRoleEnum, Long kakaoId,Long googleId) {
    this.nickname=nickname;
    this.password=encodedPassword;
    this.email=email;
    this.role=userRoleEnum;
    this.kakaoId=kakaoId;
    this.googleId=googleId;
    }

    public void updateNicknmae(String nickname) {
        this.nickname=nickname;
    }

    public void updatePassword(String encodePassword) {
        this.password=encodePassword;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }
}
