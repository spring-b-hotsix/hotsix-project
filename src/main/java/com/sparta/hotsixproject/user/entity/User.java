package com.sparta.hotsixproject.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable=false)
    private String nickname;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private Long kakaoId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING) // enum의 이름 그대로를 저장 USER -> USER
    private UserRoleEnum role;

    @Builder
    public User(String nickname, String password, String email, UserRoleEnum role) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
