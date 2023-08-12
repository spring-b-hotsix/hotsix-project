package com.sparta.hotsixproject.card.service;

import com.sparta.hotsixproject.attachment.repository.AttachmentRepository;
import com.sparta.hotsixproject.attachment.service.AttachmentService;
import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.boarduser.entity.BoardUser;
import com.sparta.hotsixproject.card.dto.CardResponseDto;
import com.sparta.hotsixproject.card.dto.DueRequestDto;
import com.sparta.hotsixproject.card.dto.MoveRequestDto;
import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.card.repository.CardRepository;
import com.sparta.hotsixproject.cardlabel.entity.CardLabel;
import com.sparta.hotsixproject.cardlabel.repository.CardLabelRepository;
import com.sparta.hotsixproject.carduser.entity.CardUser;
import com.sparta.hotsixproject.carduser.repository.CardUserRepository;
import com.sparta.hotsixproject.checklist.checklistItem.dto.ChecklistItemRequestDto;
import com.sparta.hotsixproject.checklist.checklistItem.dto.ChecklistItemResponseDto;
import com.sparta.hotsixproject.checklist.checklistItem.entity.ChecklistItem;
import com.sparta.hotsixproject.checklist.dto.ChecklistRequestDto;
import com.sparta.hotsixproject.checklist.dto.ChecklistResponseDto;
import com.sparta.hotsixproject.checklist.entity.Checklist;
import com.sparta.hotsixproject.checklist.repository.ChecklistRepository;
import com.sparta.hotsixproject.checklist.service.ChecklistServiceImpl;
import com.sparta.hotsixproject.comment.dto.CommentRequestDto;
import com.sparta.hotsixproject.comment.dto.CommentResponseDto;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import static com.mysema.commons.lang.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

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
    @Autowired
    ChecklistRepository checklistRepository;
    @Autowired
    CardUserRepository cardUserRepository;
    @Autowired
    CardLabelRepository cardLabelRepository;

    /**
     * 테스트 요구사항(시나리오)
     * 1. 카드 생성 → 카드 수정(이름, 설명, 색상, 체크리스트, 작업자, 라벨, 마감기한)
     * 2. 카드 댓글 작성
     * 3. 카드 이동
     * 4. 카드 삭제
     */

    @Test
    @DisplayName("카드 수정1(이름, 설명, 라벨, 색상 추가)")
    void updateCard1() {
        // given
        User user = createUser("nick1", "email1@gmail.com");
        Board board = createBoard(user);
        BoardUser boardUser = createBoardUser(user, board);
        Side side = createSide(board, 1);
        Card card = createCard(user, side);

        Long boardId = board.getId();
        Long sideId = side.getId();
        Long cardId = card.getId();

        // when
        /* 1. 이름 수정 */
        cardService.updateName(boardId, sideId, cardId, "card name edit");
        /* 2. 설명 추가(수정) */
        cardService.updateDesc(boardId, sideId, cardId, "card desc edit");
        /* 3-1. 라벨 생성 */
        LabelRequestDto labelDto = new LabelRequestDto("label1", "yellow");
        labelService.createLabel(boardId, labelDto, user);
        /* 3-2. 카드에 라벨 추가 */
        Label label1 = labelRepository.findByTitle("label1").orElse(null);
        labelService.createCardLabel(boardId, sideId, cardId, label1.getId(), user);
        /* 4. (카드)색상 수정 */
        cardService.updateColor(boardId, sideId, cardId, "blue");

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
    @DisplayName("카드 수정2(체크리스트 추가, 체크리스트 아이템 체크)")
    void updateCard2() {
        /**
         * 1. 체크리스트 생성 → 체크리스트 아이템 생성
         * 2. 체크리스트 수정 → 체크리스트 아이템 수정
         * 3. 체크리스트 아이템 체크
         */
        // given
        User user = createUser("nick1", "email1@gmail.com");
        Board board = createBoard(user);
        BoardUser boardUser = createBoardUser(user, board);
        Side side = createSide(board, 1);
        Card card = createCard(user, side);

        Long boardId = board.getId();
        Long sideId = side.getId();
        Long cardId = card.getId();

        // when 1
        /** 체크리스트 생성 → 체크리스트 아이템 생성 **/
        /* 1-1. 체크리스트 생성 */
        ChecklistRequestDto checkRequestDto = new ChecklistRequestDto("오늘 할 일");
        ChecklistResponseDto checkResponseDto = checklistService.createChecklist(boardId, sideId, cardId, checkRequestDto, user);

        /* 1-2. 체크리스트 아이템 생성 */
        Long checklistId = checkResponseDto.getId();

        ChecklistItemRequestDto checkItemRequestDto = new ChecklistItemRequestDto("테스트 코드 작성하기");
        ChecklistItemResponseDto checkItem1ResponseDto = checklistService.createItem(boardId, sideId, cardId, checklistId, checkItemRequestDto, user);

        checkItemRequestDto = new ChecklistItemRequestDto("발표 준비하기");
        ChecklistItemResponseDto checkItem2ResponseDto = checklistService.createItem(boardId, sideId, cardId, checklistId, checkItemRequestDto, user);

        // then 1
        assertEquals("오늘 할 일", checkResponseDto.getName()); // 체크리스트
        assertEquals("테스트 코드 작성하기", checkItem1ResponseDto.getContent()); // 체크리스트 아이템1
        assertEquals("발표 준비하기", checkItem2ResponseDto.getContent()); // 체크리스트 아이템2

        // when 2
        /** 체크리스트 수정 → 체크리스트 아이템 수정 **/
        /* 2-1. 체크리스트 수정 */
        checkRequestDto = new ChecklistRequestDto("★ 오늘 할 일 ★");
        checkResponseDto = checklistService.updateChecklistName(boardId, sideId, cardId, checklistId, checkRequestDto, user);

        /* 2-2. 체크리스트 아이템 수정 */
        checkItemRequestDto = new ChecklistItemRequestDto("테스트 코드 빨리 작성하기!!");
        checkItem1ResponseDto = checklistService.updateItemContent(boardId, sideId, cardId, checklistId, checkItem1ResponseDto.getId(), checkItemRequestDto, user);

        checkItemRequestDto = new ChecklistItemRequestDto("발표 자료 틀부터 준비하기");
        checkItem2ResponseDto = checklistService.updateItemContent(boardId, sideId, cardId, checklistId, checkItem2ResponseDto.getId(), checkItemRequestDto, user);

        // then 2
        assertEquals("★ 오늘 할 일 ★", checkResponseDto.getName());
        assertEquals("테스트 코드 빨리 작성하기!!", checkItem1ResponseDto.getContent()); // 체크리스트 아이템1
        assertEquals("발표 자료 틀부터 준비하기", checkItem2ResponseDto.getContent()); // 체크리스트 아이템2

        // when 3
        /** 체크리스트 아이템 체크 **/
        ChecklistItemResponseDto checkedItem1 = checklistService.updateItemChecked(boardId, sideId, cardId, checklistId, checkItem1ResponseDto.getId(), user); // 1번 아이템 체크

        // 2번 아이템 체크 → 해제
        checklistService.updateItemChecked(boardId, sideId, cardId, checklistId, checkItem2ResponseDto.getId(), user);
        ChecklistItemResponseDto checkedItem2 = checklistService.updateItemChecked(boardId, sideId, cardId, checklistId, checkItem2ResponseDto.getId(), user);

        // then 3
        assertTrue(checkedItem1.getChecked()); // 1번 아이템 = 체크
        assertFalse(checkedItem2.getChecked()); // 2번 아이템 = 체크 해제
    }

    @Test
    @DisplayName("카드 수정3(작업자 추가)")
    void updateCard3() {
        /**
         * 1. 보드 생성 -> 사용자 초대
         * 2. 사용자를 카드에 작업자로 추가
         */
        // given
        User user1 = createUser("nick1", "email1@email.com");
        User user2 = createUser("nick2", "email2@email.com");
        User user3 = createUser("nick3", "email3@email.com");
        Board board = createBoard(user1);
        BoardUser boardUser1 = createBoardUser(user1, board);
        BoardUser boardUser2 = createBoardUser(user2, board);
        BoardUser boardUser3 = createBoardUser(user3, board);
        Side side = createSide(board, 1);
        Card card = createCard(user1, side);

        Long boardId = board.getId();
        Long sideId = side.getId();
        Long cardId = card.getId();

        // when
        cardService.addWorker(boardId, sideId, cardId, user1.getEmail());
        cardService.addWorker(boardId, sideId, cardId, user2.getEmail());
        cardService.addWorker(boardId, sideId, cardId, user3.getEmail());

        // then
        assertEquals(user1.getEmail(), card.getCardUserList().get(0).getUser().getEmail());
        assertEquals(user2.getEmail(), card.getCardUserList().get(1).getUser().getEmail());
        assertEquals(user3.getEmail(), card.getCardUserList().get(2).getUser().getEmail());
    }

    @Test
    @DisplayName("카드 수정4(마감기한 추가, 마감기한 초과)")
    void updateCard4() {
        // given
        User user1 = createUser("nick1", "email1@email.com");
        Board board = createBoard(user1);
        BoardUser boardUser1 = createBoardUser(user1, board);
        Side side = createSide(board, 1);
        Card card = createCard(user1, side);

        Long boardId = board.getId();
        Long sideId = side.getId();
        Long cardId = card.getId();

        // when 1
        /** 마감기한 추가 **/
        LocalDateTime localDateTime = LocalDateTime.of(2023, 9, 1, 13, 0);
        DueRequestDto dueRequestDto = new DueRequestDto(localDateTime);
        CardResponseDto cardResponseDto = cardService.updateDue(boardId, sideId, cardId, dueRequestDto).getBody();

        // then 1
        assertEquals(localDateTime, cardResponseDto.getDue());

        // when 2
        /** 마감기한 초과 **/
        localDateTime = LocalDateTime.of(2023, 8, 12, 6, 0);
        dueRequestDto = new DueRequestDto(localDateTime);
        cardResponseDto = cardService.updateDue(boardId, sideId, cardId, dueRequestDto).getBody();

        // then 2
        assertTrue(cardResponseDto.isOverdue());
    }

    @Test
    @DisplayName("카드에 댓글 작성 및 수정")
    void createAndUpdateCardComment() {
        // given
        User user1 = createUser("nick1", "email1@email.com");
        Board board = createBoard(user1);
        BoardUser boardUser1 = createBoardUser(user1, board);
        Side side = createSide(board, 1);
        Card card = createCard(user1, side);

        Long boardId = board.getId();
        Long sideId = side.getId();
        Long cardId = card.getId();

        // when 1
        /** 1. 카드에 댓글 작성 **/
        CommentRequestDto requestDto = new CommentRequestDto("와 멋지다~");
        CommentResponseDto responseDto = commentService.createComment(boardId, sideId, cardId, requestDto, user1);

        // then 1
        assertEquals("와 멋지다~", responseDto.getContent());

        // when 2
        /** 2. 작성한 댓글 수정 **/
        requestDto = new CommentRequestDto("와 멋지다~ 그런데 이 부분은 수정하면 좋겠어요.");
        responseDto = commentService.updateComment(boardId, sideId, cardId, responseDto.getId(), requestDto, user1);

        // then 2
        assertEquals("와 멋지다~ 그런데 이 부분은 수정하면 좋겠어요.", responseDto.getContent());
    }

    @Test
    @DisplayName("카드 이동")
    void moveCard() {
        // given
        User user1 = createUser("nick1", "email1@email.com");
        Board board = createBoard(user1);
        BoardUser boardUser1 = createBoardUser(user1, board);
        Side side = createSide(board, 1);
        Card card1 = createCard(user1, side);
        Card card2 = createCard(user1, side);
        Card card3 = createCard(user1, side);
        Card card4 = createCard(user1, side);

        Long boardId = board.getId();
        Long sideId = side.getId();

        // when 1
        MoveRequestDto requestDto = new MoveRequestDto(side.getName(), 2);
        cardService.moveCard(boardId, sideId, card1.getId(), requestDto);

        // then 1
        assertEquals(2, card1.getPosition());
        assertEquals(1, card2.getPosition());
        assertEquals(3, card3.getPosition());
        assertEquals(4, card4.getPosition());

        // when 2
        requestDto = new MoveRequestDto(side.getName(), 1);
        cardService.moveCard(boardId, sideId, card2.getId(), requestDto);
        requestDto = new MoveRequestDto(side.getName(), 3);
        cardService.moveCard(boardId, sideId, card3.getId(), requestDto);
        requestDto = new MoveRequestDto(side.getName(), 1);
        cardService.moveCard(boardId, sideId, card4.getId(), requestDto);

        // then 2
        assertEquals(3, card1.getPosition());
        assertEquals(2, card2.getPosition());
        assertEquals(4, card3.getPosition());
        assertEquals(1, card4.getPosition());
    }

    @Test
    @DisplayName("카드 삭제")
    void deleteCard() {
        /**
         * 시나리오:
         * 카드 생성
         * → 체크리스트, 작업자, 라벨 추가
         * → 카드 삭제 후
         * CardUser, Card.checklistlist, Card.cardlabel 확인
         */
        // given
        User user1 = createUser("nick1", "email1@email.com");
        Board board = createBoard(user1);
        BoardUser boardUser1 = createBoardUser(user1, board);
        Side side = createSide(board, 1);
        Card card = createCard(user1, side);

        Long boardId = board.getId();
        Long sideId = side.getId();
        Long cardId = card.getId();

        // when 1
        /** 체크리스트, 작업자, 라벨 추가 **/
        ChecklistRequestDto checkRequestDto = new ChecklistRequestDto("오늘 할 일");
        ChecklistResponseDto checkResponseDto = checklistService.createChecklist(boardId, sideId, cardId, checkRequestDto, user1);

        cardService.addWorker(boardId, sideId, cardId, user1.getEmail());

        LabelRequestDto labelDto = new LabelRequestDto("label1", "yellow");
        labelService.createLabel(boardId, labelDto, user1);
        Label label = labelRepository.findByTitle("label1").orElse(null);
        labelService.createCardLabel(boardId, sideId, cardId, label.getId(), user1);

        // then 1
        assertEquals("오늘 할 일", checkResponseDto.getName()); // 체크리스트
        assertEquals(user1.getEmail(), card.getCardUserList().get(0).getUser().getEmail()); // 작업자
        assertEquals("label1", label.getTitle()); // 라벨
        assertEquals("yellow", label.getColor());
        assertEquals("label1", card.getCardLabelList().get(0).getLabel().getTitle());

        // when 2
        /** 카드 삭제 후 CardUser, Card.checklistlist, Card.cardlabel 확인 **/
        labelService.deleteCardLabel(boardId, sideId, cardId, label.getId(), user1);
        cardService.deleteWorker(boardId, sideId, cardId, user1.getEmail());
        checklistService.deleteChecklist(boardId, sideId, cardId, checkResponseDto.getId(), user1);

        CardLabel deletedCardLabel = cardLabelRepository.findByCard_idAndLabel_id(cardId, label.getId()).orElse(null);
        CardUser deletedCardUser = cardUserRepository.findByCard_IdAndUser_Email(cardId, user1.getEmail()).orElse(null);
        Checklist deletedChecklist = checklistRepository.findById(checkResponseDto.getId()).orElse(null);

        // then 2
        assertNull(deletedChecklist);
        assertNull(deletedCardUser);
        assertNull(deletedCardLabel);
    }


    private Card createCard(User user, Side side) {
        Card card = new Card("card", side.getCardList().size() + 1, user, side);
        side.addCard(card);
        em.persist(card);
        return card;
    }
    private Side createSide(Board board, int pos) {
        Side side = new Side("side1", 1024 * pos, board);
        board.addSide(side);
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
    private User createUser(String nickname, String email) {
        String encodePw = passwordEncoder.encode("Password@1234");
        User user = new User(nickname, encodePw, email, UserRoleEnum.USER);
        em.persist(user);
        return user;
    }
}