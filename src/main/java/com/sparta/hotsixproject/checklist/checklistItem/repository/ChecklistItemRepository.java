package com.sparta.hotsixproject.checklist.checklistItem.repository;

import com.sparta.hotsixproject.checklist.checklistItem.entity.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Long> {
}
