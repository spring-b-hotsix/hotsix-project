package com.sparta.hotsixproject.carduser.entity;

import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.label.entity.Label;
import com.sparta.hotsixproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    public CardUser(Card card, User user) {
        this.card = card;
        this.user = user;
    }
}
