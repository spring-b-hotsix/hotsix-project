package com.sparta.hotsixproject.attachment.entity;

import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.common.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attachment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fileName;

    @Column
    private String source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Card card;

    public Attachment(String fileName, String source, Card card) {
        this.fileName = fileName;
        this.source = source;
        this.card = card;
    }

    public void updateName(String fileName) {
        this.fileName = fileName;
    }
}
