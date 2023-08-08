package com.sparta.hotsixproject.card.repository;

import com.sparta.hotsixproject.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findBySide_Board_IdAndSide_IdOrderByPositionAscIdAsc(Long id, Long id1);
    List<Card> findBySide_Board_IdAndSide_IdOrderByPositionDescIdAsc(Long id, Long id1);
    List<Card> findBySide_Board_IdAndSide_Id(Long id, Long id1);
    Card findBySide_Board_IdAndSide_IdAndId(Long id, Long id1, Long id2);

}
