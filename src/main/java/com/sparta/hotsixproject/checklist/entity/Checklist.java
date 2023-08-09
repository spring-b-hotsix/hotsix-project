package com.sparta.hotsixproject.checklist.entity;

import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.checklist.checklistItem.entity.ChecklistItem;
import com.sparta.hotsixproject.checklist.dto.ChecklistRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Checklists")
@NoArgsConstructor
public class Checklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cardId", nullable = false)
    private Card card;

    @Column
    private String name;

    @OneToMany(mappedBy = "checklist", orphanRemoval = true)
    private List<ChecklistItem> checklistItems;

    public Checklist(Card card, ChecklistRequestDto requestDto) {
        this.card = card;
        this.name = requestDto.getName();
    }

    public void updateChecklist(ChecklistRequestDto requestDto) {
        this.name = requestDto.getName();
    }
}
