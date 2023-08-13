package com.sparta.hotsixproject.boarduser.repository;

import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.boarduser.entity.BoardUser;
import com.sparta.hotsixproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardUserRepository extends JpaRepository<BoardUser, Long> {
    boolean existsByBoard_IdAndUser_Id(Long boardId, Long userId);
    boolean existsByUser_Id(Long id);
    BoardUser findByUser_Id(Long id);
    Optional<BoardUser> findByUser_EmailAndBoard_Id(String email, Long Id);
    List<BoardUser> findByBoard_Id(Long Id);
    List<BoardUser> findAllByUser(User user);

    Optional<BoardUser> findByUserAndBoard(User user, Board board);
}
