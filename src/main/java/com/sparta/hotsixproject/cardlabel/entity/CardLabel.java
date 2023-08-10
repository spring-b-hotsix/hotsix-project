package com.sparta.hotsixproject.cardlabel.entity;

import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.label.entity.Label;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardLabel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cardId", nullable = false)
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "labelId", nullable = false)
    private Label label;

    public CardLabel(Card card, Label label) {
        this.card = card;
        this.label = label;
    }
}
