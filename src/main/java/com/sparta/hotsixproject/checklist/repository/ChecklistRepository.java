package com.sparta.hotsixproject.checklist.repository;

import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.checklist.entity.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
    List<Checklist> findAllByCard(Card card);
}
