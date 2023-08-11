package com.sparta.hotsixproject.card.service;

import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.boarduser.entity.BoardUser;
import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.card.repository.CardRepository;
import com.sparta.hotsixproject.cardlabel.entity.CardLabel;
import com.sparta.hotsixproject.checklist.service.ChecklistServiceImpl;
import com.sparta.hotsixproject.comment.repository.CommentRepository;
import com.sparta.hotsixproject.comment.service.CommentServiceImpl;
import com.sparta.hotsixproject.label.dto.LabelRequestDto;
import com.sparta.hotsixproject.label.entity.Label;
import com.sparta.hotsixproject.label.repository.LabelRepository;
import com.sparta.hotsixproject.label.service.LabelService;
import com.sparta.hotsixproject.side.entity.Side;
import com.sparta.hotsixproject.user.UserRoleEnum;
import com.sparta.hotsixproject.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class CardServiceTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CardService cardService;
    @Autowired
    CommentServiceImpl commentService;
    @Autowired
    LabelService labelService;
    @Autowired
    ChecklistServiceImpl checklistService;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    LabelRepository labelRepository;

    /**
     * 테스트 요구사항(시나리오)
     * 1. 카드 생성 → 카드 수정(이름, 설명, 색상, 체크리스트, 작업자, 라벨, 마감기한)
     * 2. 카드 첨부파일? (되나??)
     * 3. 카드 댓글 작성
     * 4. 카드 이동
     * 5. 카드 삭제
     */

    @Test
    @DisplayName("카드 수정1(이름, 설명, 라벨, 색상)")
    void updateCard1() {
        // given
        User user = createUser();
        Board board = createBoard(user);
        BoardUser boardUser = createBoardUser(user, board);
        Side side = createSide(board, 1);
        Card card = createCard(user, side);

        // when
        /* 1. 이름 수정 */
        cardService.updateName(board.getId(), side.getId(), card.getId(), "card name edit");
        /* 2. 설명 추가(수정) */
        cardService.updateDesc(board.getId(), side.getId(), card.getId(), "card desc edit");
        /* 3-1. 라벨 생성 */
        LabelRequestDto labelDto = new LabelRequestDto("label1", "yellow");
        labelService.createLabel(board.getId(), labelDto, user);
        /* 3-2. 카드에 라벨 추가 */
        Label label1 = labelRepository.findByTitle("label1").orElse(null);
        labelService.createCardLabel(board.getId(), side.getId(), card.getId(), label1.getId(), user);
        CardLabel cardLabel = new CardLabel(card, label1);
        card.addCardLabel(cardLabel);
        label1.addCardLabel(cardLabel);
        /* 4. (카드)색상 수정 */
        cardService.updateColor(board.getId(), side.getId(), card.getId(), "blue");

        // then
        /* 1. 이름 수정 */
        assertEquals("card name edit", card.getName());
        /* 2. 설명 추가(수정) */
        assertEquals("card desc edit", card.getDescription());
        /* 3-1. 라벨 생성 */
        assertEquals("label1", label1.getTitle());
        assertEquals("yellow", label1.getColor());
        /* 3-2. 카드에 라벨 추가 */
        assertEquals("label1", card.getCardLabelList().get(0).getLabel().getTitle());
        /* 4. (카드)색상 수정 */
        assertEquals("blue", card.getColor());
    }

    @Test
    @DisplayName("카드 수정2(체크리스트, 마감기한, 작업자)")
    void updateCard2() {
    }

    @Test
    @DisplayName("첨부파일 확인")
    void createCardAttachFile() {
    }

    @Test
    @DisplayName("카드에 댓글 작성")
    void createCardComment() {
    }

    @Test
    @DisplayName("카드 이동")
    void moveCard() {
    }

    @Test
    @DisplayName("카드 삭제")
    void deleteCard() {
    }

    private Card createCard(User user, Side side) {
        Card card = new Card("card", side.getCardList().size() + 1, user, side);
        em.persist(card);
        return card;
    }

    private Side createSide(Board board, int pos) {
        Side side = new Side("side1", 1024 * pos, board);
        em.persist(side);
        return side;
    }

    private BoardUser createBoardUser(User user, Board board) {
        BoardUser boardUser = new BoardUser(user, board);
        em.persist(boardUser);
        return boardUser;
    }

    private Board createBoard(User user) {
        Board board = new Board("board1", "descr1", user, 255, 255, 255);
        user.addBoard(board);
        em.persist(board);
        return board;
    }

    private User createUser() {
        String encodePw = passwordEncoder.encode("Password@1234");
        User user = new User("nick1", encodePw, "email1@email.com", UserRoleEnum.USER);
        em.persist(user);
        return user;
    }
}