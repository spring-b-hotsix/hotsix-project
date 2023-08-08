package com.sparta.hotsixproject.checklist.checklistItem.entity;

import com.sparta.hotsixproject.checklist.checklistItem.dto.ChecklistItemRequestDto;
import com.sparta.hotsixproject.checklist.entity.Checklist;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ChecklistItems")
@NoArgsConstructor
public class ChecklistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklistId", nullable = false)
    private Checklist checklist;

    @Column(name = "checked")
    private boolean checked;

    @Column(name = "content", nullable = false)
    private String content;

    public ChecklistItem(Checklist checklist, ChecklistItemRequestDto requestDto) {
        this.checklist = checklist;
        this.content = requestDto.getContent();
    }

    public void updateChecked() {
        this.checked = !checked;
    }

    public void updateContent(ChecklistItemRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
