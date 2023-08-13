package com.sparta.hotsixproject.user.entity;

import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.carduser.entity.CardUser;
import com.sparta.hotsixproject.notification.entity.Notification;
import com.sparta.hotsixproject.user.UserRoleEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"boardList", "cardList", "notification"})
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Card> cardList = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<CardUser> cardUserList = new ArrayList<>();

//    @OneToMany(mappedBy = "login_user", orphanRemoval = true)
//    private List<Notification> notificationList = new ArrayList<>();

    @OneToMany(mappedBy = "editor", orphanRemoval = true)
    private List<Notification> notificationList = new ArrayList<>();

    @Column
    private Long kakaoId;

    @Column
    private String googleId;

    public User(String nickname, String password, String email, UserRoleEnum role) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User(String nickname, String encodedPassword, String email, UserRoleEnum userRoleEnum, Long kakaoId, String googleId) {
        this.nickname = nickname;
        this.password = encodedPassword;
        this.email = email;
        this.role = userRoleEnum;
        this.kakaoId = kakaoId;
        this.googleId = googleId;
    }

    public void updateNicknmae(String nickname) {
        this.nickname = nickname;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }

    public User googleIdUpdate(String googleId) {
        this.googleId = googleId;
        return this;
    }

    public void updatePassword(String encodePassword) {
        this.password = encodePassword;
    }

    public void updateImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
