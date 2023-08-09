package com.sparta.hotsixproject.cardlabel.repository;

import com.sparta.hotsixproject.cardlabel.entity.CardLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardLabelRepository extends JpaRepository<CardLabel, Long> {
    List<CardLabel> findAllByCard_Id(Long cardId);

    CardLabel findByCard_idAndLabel_id(Long cardId, Long labelId);
}
