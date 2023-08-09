package com.sparta.hotsixproject.comment.entity;

import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.comment.CommentStatus;
import com.sparta.hotsixproject.comment.dto.CommentRequestDto;
import com.sparta.hotsixproject.common.entity.TimeStamped;
import com.sparta.hotsixproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.sparta.hotsixproject.comment.CommentStatus.ORIGINAL;

@Entity
@Getter
@Setter
@Table(name = "comments")
@NoArgsConstructor
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(nullable = false)
    private CommentStatus commentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cardId", nullable = false)
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public Comment(Card card, CommentRequestDto requestDto, User user) {
        this.card = card;
        this.content = requestDto.getContent();
        this.commentStatus = ORIGINAL;
        this.user = user;
    }

    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

    public void updateStatus(CommentStatus status) {
        this.commentStatus = status;
    }
}
