package com.sparta.hotsixproject.notification.entity;

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

    // DB에 저장되지 않는 가상 필드
    @Transient
    private Duration timeSinceModified;

//    // 사람에게 뜨는 알림, 일단 전체 유저가 다 볼 수 있도록 주석처리
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(nullable = false)
//    private User login_user;
    
    // 알림 발생 시킨 editor
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User editor;

    public Notification(NotificationTitle title, String detail, LocalDateTime modifiedAt, User editor) {
        this.title = title;
        this.detail = detail;
        this.timeSinceModified = Duration.between(modifiedAt, LocalDateTime.now());
        this.editor = editor;
    }
}
