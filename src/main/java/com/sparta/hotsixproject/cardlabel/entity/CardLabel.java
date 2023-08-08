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
    @JoinColumn
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn
    private Label label;

    public CardLabel(Card card, Label label) {
        this.card = card;
        this.label = label;
    }
}
