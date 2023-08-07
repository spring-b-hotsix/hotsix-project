package com.sparta.hotsixproject.board.repository;

import com.sparta.hotsixproject.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Override
    Optional<Board> findById(Long id);
}
