package com.sparta.hotsixproject.notification.entity;

import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.notification.NotificationTitle;
import com.sparta.hotsixproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(value = EnumType.STRING)
    private NotificationTitle title;

    @Column
    private String detail;

    @Column
    private String timeSinceModified;

//    // 사람에게 뜨는 알림, 일단 전체 유저가 다 볼 수 있도록 주석처리
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(nullable = false)
//    private User login_user;
    
    // 알림 발생 시킨 editor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User editor;

    // 알림이 속한 board
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Board board;

    public Notification(NotificationTitle title, String detail, LocalDateTime modifiedAt, User editor, Board board) {
        this.title = title;
        this.detail = detail;
        this.timeSinceModified = Duration.between(modifiedAt, LocalDateTime.now()).toString();
        this.editor = editor;
        this.board = board;
    }
}
