package com.sparta.hotsixproject.boarduser.repository;

import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.boarduser.controller.BoardUser;
import com.sparta.hotsixproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {
    List<BoardUser> findAllByUser(User user);

    Optional<BoardUser> findByUserAndBoard(User user, Board board);
}
