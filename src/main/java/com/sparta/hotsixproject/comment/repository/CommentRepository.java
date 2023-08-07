package com.sparta.hotsixproject.comment.repository;

import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByCardOrderByCreatedAtDesc(Card card);
}
