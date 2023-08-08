package com.sparta.hotsixproject.attachment.entity;

import com.sparta.hotsixproject.card.entity.Card;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attachment {
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

    public Attachment(String source, Card card) {
        this.fileName = source;
        this.source = source;
        this.card = card;
    }

    public void updateName(String fileName) {
        this.fileName = fileName;
    }
}
