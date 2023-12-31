package com.sparta.hotsixproject.label.repository;

import com.sparta.hotsixproject.label.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
    List<Label> findAllByBoard_Id(Long boardId);
}
