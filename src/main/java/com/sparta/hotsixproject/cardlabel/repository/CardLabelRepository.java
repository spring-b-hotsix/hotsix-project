package com.sparta.hotsixproject.cardlabel.repository;

import com.sparta.hotsixproject.cardlabel.entity.CardLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardLabelRepository extends JpaRepository<CardLabel, Long> {
}
