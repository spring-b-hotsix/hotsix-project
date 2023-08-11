package com.sparta.hotsixproject.board.service;

import com.sparta.hotsixproject.board.dto.BoardRequestDto;
import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.board.repository.BoardRepository;
import com.sparta.hotsixproject.boarduser.entity.BoardUser;
import com.sparta.hotsixproject.boarduser.repository.BoardUserRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class BoardServiceTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    BoardServiceImpl boardService;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    BoardUserRepository boardUserRepository;

    @Test
    @DisplayName("보드 생성")
    void createBoard() {
        // given
        User newUser1 = createUser("new1", "new1@email.com");

        // when
        BoardRequestDto requestDto = new BoardRequestDto("board1", "desc1", 255, 255, 255);
        boardService.createBoard(requestDto, newUser1);
        Board board = boardRepository.findByUser(newUser1).orElse(null); // 뭘로 찾지??

        // then
        assertEquals("board1", board.getName()); // 보드 이름 확인
        assertEquals(newUser1.getNickname(), board.getUser().getNickname()); // 생성 유저 확인
    }

    @Test
    @DisplayName("보드에 사용자 초대 성공")
    void inviteBoardSuccess() {
        // given
        User newUser1 = createUser("new1", "new1@email.com");
        User newUser2 = createUser("new2", "new2@email.com");
        User newUser3 = createUser("new3", "new3@email.com");
        Board board1 = createBoard(newUser1);
        newUser1.addBoard(board1);
        BoardUser boardUser1 = createBoardUser(newUser1, board1);

        // when
        /**
         * 시나리오?: newUser1이 만든 board1에 newUser2와 newUser3를 초대할 예정~
         */
        boardService.inviteBoard(board1.getId(), newUser2.getEmail(), newUser1);
        boardService.inviteBoard(board1.getId(), newUser3.getEmail(), newUser1);

        BoardUser boardUser2 = boardUserRepository.findByUserAndBoard(newUser2, board1).orElse(null);
        BoardUser boardUser3 = boardUserRepository.findByUserAndBoard(newUser3, board1).orElse(null);

        // then
        assertNotNull(boardUser2);
        assertNotNull(boardUser3);
    }

    @Test
    @DisplayName("보드에 사용자 초대 실패")
    void inviteBoardFail() throws IllegalArgumentException {
        // given
        User newUser1 = createUser("new1", "new1@email.com");
        User newUser2 = createUser("new2", "new2@email.com");
        Board board1 = createBoard(newUser1);
        newUser1.addBoard(board1);
        BoardUser boardUser1 = createBoardUser(newUser1, board1);

        // when
        /**
         * 시나리오?: newUser1이 만든 board1에 사용자를 초대
         *      1. 없는 이메일(사용자), 2. newUser2를 초대 후 -> 한 번 더 초대(이러면 실패함)
         */
        IllegalArgumentException exception2 = assertThrows(     // 없는 email로 초대를 요청한 경우
                IllegalArgumentException.class, () -> {
                    boardService.inviteBoard(board1.getId(), "new3@email.com", newUser1); // 없는 이메일
                }
        );
        boardService.inviteBoard(board1.getId(), newUser2.getEmail(), newUser1);
        IllegalArgumentException exception3 = assertThrows(     // 초대 후 재 초대 시도
                IllegalArgumentException.class, () -> {
                    boardService.inviteBoard(board1.getId(), newUser2.getEmail(), newUser1);
                }
        );

        // then
        assertEquals("입력한 이메일로 가입한 사용자가 존재하지 않습니다.", exception2.getMessage());
        assertEquals("해당 유저는 이미 보드의 멤버입니다.", exception3.getMessage());
    }

    @Test
    @DisplayName("내가 생성한 보드와 초대된 보드 확인하기")
    void getMyBoardsAndGuestBoards() {

    }

    @Test
    @DisplayName("보드 수정")
    void updateBoard() {
    }

    @Test
    @DisplayName("보드 삭제")
    void deleteBoard() {
    }


    private BoardUser createBoardUser(User user, Board board) {
        BoardUser boardUser = new BoardUser(user, board);
        em.persist(boardUser);
        return boardUser;
    }

    private Board createBoard(User user) {
        Board board = new Board("board1", "descr1", user, 255, 255, 255);
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