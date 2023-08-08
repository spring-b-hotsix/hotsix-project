package com.sparta.hotsixproject.label.entity;

import com.sparta.hotsixproject.cardlabel.entity.CardLabel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String color;

    @OneToMany(mappedBy = "label", orphanRemoval = true)
    private List<CardLabel> cardLabelList = new ArrayList<>();

    public Label(String title, String color) {
        this.title = title;
        this.color = color;
    }
}
