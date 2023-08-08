package com.sparta.hotsixproject.board.repository;

import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByUser(User user);

}
