package com.sparta.hotsixproject.common.dummy;

import com.github.javafaker.Faker;
import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.board.repository.BoardRepository;
import com.sparta.hotsixproject.boarduser.entity.BoardUser;
import com.sparta.hotsixproject.boarduser.repository.BoardUserRepository;
import com.sparta.hotsixproject.card.entity.Card;
import com.sparta.hotsixproject.card.repository.CardRepository;
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
    private final LabelRepository labelRepository;

    // 상수
    private static final int COUNT = 5;

    @Autowired
    public DummyGenerator(
            PasswordEncoder passwordEncoder,
            UserRepository userRepository, BoardRepository boardRepository, BoardUserRepository boardUserRepository,
            SideRepository sideRepository, CardRepository cardRepository, LabelRepository labelRepository
    ) {
        this.faker = new Faker();
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.boardUserRepository = boardUserRepository;
        this.sideRepository = sideRepository;
        this.cardRepository = cardRepository;
        this.labelRepository = labelRepository;
    }

    // 실행
    @Override
    public void run(String... args) throws Exception {
        List<User> userList = DummyUserGenerator();
        Map<User, Board> boardMap = DummyBoardGenerator(userList);
        Map<Board, List<Side>> sideListMap = DummySideGenerator(userList, boardMap);
        Map<Side, List<Card>> cardListMap = DummyCardGenerator(userList, boardMap, sideListMap);
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

            Board board = new Board(name, description, userList.get(i), 255, 255, 255);
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
        List<Side> sideList = new ArrayList<>();
        for (int i = 0; i < COUNT; i++) {
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

            for(int m = 0; m < sideList.size(); m++) {
                List<Card> cardList = new ArrayList<>();
                Card card1 = new Card("card1", m, user, sideList.get(m));
                Card card2 = new Card("card2", m, user, sideList.get(m));
                Card card3 = new Card("card3", m, user, sideList.get(m));

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




    // Card 세부 (라벨, 체크리스트, 작업자 등)
}
