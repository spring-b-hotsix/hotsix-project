package com.sparta.hotsixproject.common.dummy;

import com.github.javafaker.Faker;
import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.board.repository.BoardRepository;
import com.sparta.hotsixproject.boarduser.entity.BoardUser;
import com.sparta.hotsixproject.boarduser.repository.BoardUserRepository;
import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.card.repository.CardRepository;
import com.sparta.hotsixproject.carduser.entity.CardUser;
import com.sparta.hotsixproject.carduser.repository.CardUserRepository;
import com.sparta.hotsixproject.checklist.checklistItem.repository.ChecklistItemRepository;
import com.sparta.hotsixproject.checklist.repository.ChecklistRepository;
import com.sparta.hotsixproject.comment.dto.CommentRequestDto;
import com.sparta.hotsixproject.comment.entity.Comment;
import com.sparta.hotsixproject.comment.repository.CommentRepository;
import com.sparta.hotsixproject.label.entity.Label;
import com.sparta.hotsixproject.label.repository.LabelRepository;
import com.sparta.hotsixproject.side.entity.Side;
import com.sparta.hotsixproject.side.repository.SideRepository;
import com.sparta.hotsixproject.user.UserRoleEnum;
import com.sparta.hotsixproject.user.entity.User;
import com.sparta.hotsixproject.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DummyGenerator implements CommandLineRunner {
    private final Faker faker;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardUserRepository boardUserRepository;
    private final SideRepository sideRepository;
    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;
    private final LabelRepository labelRepository;
    private final ChecklistRepository checklistRepository;
    private final ChecklistItemRepository checklistItemRepository;
    private final CardUserRepository cardUserRepository;

    // 상수
    private static final int COUNT = 3;

    @Autowired
    public DummyGenerator(
            PasswordEncoder passwordEncoder, UserRepository userRepository, BoardRepository boardRepository,
            BoardUserRepository boardUserRepository, SideRepository sideRepository, CardRepository cardRepository,
            CommentRepository commentRepository, LabelRepository labelRepository, ChecklistRepository checklistRepository,
            ChecklistItemRepository checklistItemRepository, CardUserRepository cardUserRepository
    ) {
        this.faker = new Faker();
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.boardUserRepository = boardUserRepository;
        this.sideRepository = sideRepository;
        this.cardRepository = cardRepository;
        this.commentRepository = commentRepository;
        this.labelRepository = labelRepository;
        this.checklistRepository = checklistRepository;
        this.checklistItemRepository = checklistItemRepository;
        this.cardUserRepository = cardUserRepository;
    }

    // 실행
    @Override
    public void run(String... args) throws Exception {
        // 사용자
        List<User> userList = DummyUserGenerator();
        // 보드
        Map<User, Board> boardMap = DummyBoardGenerator(userList);
        // 컬럼
        Map<Board, List<Side>> sideListMap = DummySideGenerator(userList, boardMap);
        // 카드
        Map<Side, List<Card>> cardListMap = DummyCardGenerator(userList, boardMap, sideListMap);
        // 댓글 - 전체 중 한 개의 카드에만 생성함
        Map<Card, List<Comment>> commentListMap = DummyCommentGenerator(userList, boardMap, sideListMap, cardListMap);
        // 라벨
        List<Label> labelList = DummyLabelGenerator(userList, boardMap);
        // 체크리스트 - 전체 중 한 개의 카드에만 생성함

        // 작업자 초대 - 전체 중 한 개의 카드에만 생성함
    }

    // User
    private List<User> DummyUserGenerator() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < COUNT; i++) {
            String nickname = faker.name().username();
            String password = passwordEncoder.encode("Password@1234");
            String email = faker.internet().emailAddress();

            User user = new User(nickname, password, email, UserRoleEnum.USER);
            userRepository.save(user);

            userList.add(user);
        }
        return userList;
    }

    // Board
    private Map<User, Board> DummyBoardGenerator(List<User> userList) {
        Map<User, Board> boardMap = new HashMap<>();
        for (int i = 0; i < COUNT; i++) {
            String name = "board" + (i + 1);
            String description = "설명" + (i + 1);

            int red = (int) (Math.random() * 256);
            int green = (int) (Math.random() * 256);
            int blue = (int) (Math.random() * 256);

            Board board = new Board(name, description, userList.get(i), red, green, blue);
            BoardUser boardUser = new BoardUser(userList.get(i), board);
            boardRepository.save(board);
            boardUserRepository.save(boardUser);

            boardMap.put(userList.get(i), board);
        }
        return boardMap;
    }

    // Side(컬럼)
    private Map<Board, List<Side>> DummySideGenerator(List<User> userList, Map<User, Board> boardMap) {
        Map<Board, List<Side>> sideListMap = new HashMap<>();
        for (int i = 0; i < COUNT; i++) {
            List<Side> sideList = new ArrayList<>();
            User user = userList.get(i);
            Board board = boardMap.get(user);

            Side side1 = new Side("To do", 1024, board);
            Side side2 = new Side("Doing", 2048, board);
            Side side3 = new Side("Done", 3072, board);

            sideRepository.save(side1);
            sideRepository.save(side2);
            sideRepository.save(side3);

            sideList.add(side1);
            sideList.add(side2);
            sideList.add(side3);
            sideListMap.put(board, sideList);
        }
        return sideListMap;
    }

    // Card
    private Map<Side, List<Card>> DummyCardGenerator(List<User> userList, Map<User, Board> boardMap, Map<Board, List<Side>> sideListMap) {
        Map<Side, List<Card>> cardListMap = new HashMap<>();
        for (int i = 0; i < COUNT; i++) {
            User user = userList.get(i);
            Board board = boardMap.get(user);
            List<Side> sideList = sideListMap.get(board);
            for (int m = 0; m < sideList.size(); m++) {
                List<Card> cardList = new ArrayList<>();
                Card card1 = new Card("card1", 1, user, sideList.get(m));
                Card card2 = new Card("card2", 2, user, sideList.get(m));
                Card card3 = new Card("card3", 3, user, sideList.get(m));

                cardRepository.save(card1);
                cardRepository.save(card2);
                cardRepository.save(card3);

                cardList.add(card1);
                cardList.add(card2);
                cardList.add(card3);
                cardListMap.put(sideList.get(m), cardList);
            }
        }
        return cardListMap;
    }

    // Comment
    private Map<Card, List<Comment>> DummyCommentGenerator(List<User> userList, Map<User, Board> boardMap, Map<Board, List<Side>> sideListMap, Map<Side, List<Card>> cardListMap) {
        Map<Card, List<Comment>> commentListMap = new HashMap<>();
        List<Comment> commentList = new ArrayList<>();

        User user = userList.get(0);
        Board board = boardMap.get(user);
        Side side = sideListMap.get(board).get(0);
        Card card = cardListMap.get(side).get(0);

        List<String> contentList = new ArrayList<>();
        contentList.add("할 일이 많네요");
        contentList.add("힘냅시다~");

        for (int i = 0; i < contentList.size(); i++) {
            CommentRequestDto requestDto = new CommentRequestDto(contentList.get(i));
            Comment comment = new Comment(card, requestDto, user);

            commentRepository.save(comment);
            commentList.add(comment);
        }
        commentListMap.put(card, commentList);

        return commentListMap;
    }

    // Card 세부 (라벨, 체크리스트, 작업자 등)
    // 1. 라벨
    private List<Label> DummyLabelGenerator(List<User> userList, Map<User, Board> boardMap) {
        List<Label> labelList = new ArrayList<>();
        for (int i = 0; i < COUNT; i++) {
            User user = userList.get(i);
            Board board = boardMap.get(user);
            String title = "label" + i;

            int red = (int) (Math.random() * 256);
            int green = (int) (Math.random() * 256);
            int blue = (int) (Math.random() * 256);

            Label label = new Label(board, title, red, green, blue);

            labelRepository.save(label);
            labelList.add(label);
        }
        return labelList;
    }

    // 2. 체크리스트


    // 3. 작업자 추가
    private Map<Card, List<CardUser>> DummyCardUserGenerator(List<User> userList, Map<User, Board> boardMap, Map<Board, List<Side>> sideListMap, Map<Side, List<Card>> cardListMap) {
        Map<Card, List<CardUser>> cardUserListMap = new HashMap<>();
        List<CardUser> cardUserList = new ArrayList<>();

        User user = userList.get(0);
        Board board = boardMap.get(user);
        Side side = sideListMap.get(board).get(0);
        Card card = cardListMap.get(side).get(0);

        /// 사용자를 보드에 초대
        User inviteUser1 = userList.get(1);
        User inviteUser2 = userList.get(2);

        BoardUser boardUser1 = new BoardUser(inviteUser1, board);
        BoardUser boardUser2 = new BoardUser(inviteUser2, board);

        boardUserRepository.save(boardUser1);
        boardUserRepository.save(boardUser2);

        /// 사용자를 카드에 작업자로 추가
        CardUser cardUser1 = new CardUser(card, inviteUser1);
        CardUser cardUser2 = new CardUser(card, inviteUser2);

        cardUserRepository.save(cardUser1);
        cardUserRepository.save(cardUser2);

        cardUserList.add(cardUser1);
        cardUserList.add(cardUser2);
        cardUserListMap.put(card, cardUserList);

        return cardUserListMap;
    }
}