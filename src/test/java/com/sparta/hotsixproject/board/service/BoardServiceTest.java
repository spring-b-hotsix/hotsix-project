package com.sparta.hotsixproject.board.service;

import com.sparta.hotsixproject.board.dto.BoardRequestDto;
import com.sparta.hotsixproject.board.dto.BoardResponseDto;
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

import java.util.List;

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
        BoardUser boardUser1 = createBoardUser(newUser1, board1);

        // when
        /**
         * 시나리오: newUser1이 만든 board1에 newUser2와 newUser3를 초대
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
    @DisplayName("내가 생성한 보드와 초대된 보드 조회")
    void getMyBoardsAndGuestBoards() {
        // given
        /**
         * 초기 접근허가 보드 개수 >
         * newUser1: 본인 2, 초대 0
         * newUser2: 본인 1, 초대 0
         * newUser3: 본인 0, 초대 0
         */
        /* 보드 생성 */
        User newUser1 = createUser("new1", "new1@email.com");
        User newUser2 = createUser("new2", "new2@email.com");
        User newUser3 = createUser("new3", "new3@email.com");

        Board board1 = createBoard(newUser1);
        BoardUser boardUser1to1 = createBoardUser(newUser1, board1);
        Board board2 = createBoard(newUser1);
        BoardUser boardUser1to2 = createBoardUser(newUser1, board2);
        Board board3 = createBoard(newUser2);
        BoardUser boardUser2to3 = createBoardUser(newUser2, board3);

        /**
         * 초대 진행 후 >
         * newUser1: 본인 2(1,2번), 초대 1(3번)
         * newUser2: 본인 1(3번), 초대 2(1,2번)
         * newUser3: 본인 0, 초대 3(1,2,3번)
         */
        /* 보드에 사용자 초대 */
        boardService.inviteBoard(board1.getId(), newUser2.getEmail(), board1.getUser()); // 초대할 보드, 초대할 사용자, 초대하는 사용자
        boardService.inviteBoard(board1.getId(), newUser3.getEmail(), board1.getUser()); // 초대할 보드, 초대할 사용자, 초대하는 사용자
        boardService.inviteBoard(board2.getId(), newUser2.getEmail(), board2.getUser()); // 초대할 보드, 초대할 사용자, 초대하는 사용자
        boardService.inviteBoard(board2.getId(), newUser3.getEmail(), board2.getUser()); // 초대할 보드, 초대할 사용자, 초대하는 사용자
        boardService.inviteBoard(board3.getId(), newUser1.getEmail(), board3.getUser()); // 초대할 보드, 초대할 사용자, 초대하는 사용자
        boardService.inviteBoard(board3.getId(), newUser3.getEmail(), board3.getUser()); // 초대할 보드, 초대할 사용자, 초대하는 사용자

        // when
        /* 1. 본인이 생성한 보드 */
        List<BoardResponseDto> user1MyBoardDtoList = boardService.getMyBoards(newUser1);
        List<BoardResponseDto> user2MyBoardDtoList = boardService.getMyBoards(newUser2);
        List<BoardResponseDto> user3MyBoardDtoList = boardService.getMyBoards(newUser3);

        /* 2. 본인이 초대된 보드 */
        List<BoardResponseDto> user1GuestBoardDtoList = boardService.getGuestBoards(newUser1);
        List<BoardResponseDto> user2GuestBoardDtoList = boardService.getGuestBoards(newUser2);
        List<BoardResponseDto> user3GuestBoardDtoList = boardService.getGuestBoards(newUser3);

        // then
        /* 1. 본인이 생성한 보드 개수 */
        assertEquals(2, user1MyBoardDtoList.size()); // user1 = 2개
        assertEquals(1, user2MyBoardDtoList.size()); // user2 = 1개
        assertEquals(0, user3MyBoardDtoList.size()); // user3 = null

        /* 2. 본인이 초대된 보드 개수 */
        assertEquals(1, user1GuestBoardDtoList.size()); // user1 = 1개
        assertEquals(2, user2GuestBoardDtoList.size()); // user2 = 2개
        assertEquals(3, user3GuestBoardDtoList.size()); // user3 = 3개
    }

    @Test
    @DisplayName("보드 삭제")
    void deleteBoard() {
        // given
        /**
         * 시나리오:
         * 보드 생성
         * → 보드에 다른 사용자 초대
         * → 보드 삭제 후 BoardUser 테이블, User.boardList 확인
         */
        /* 보드 생성 */
        User newUser1 = createUser("new1", "new1@email.com");
        User newUser2 = createUser("new2", "new2@email.com");
        Board board1 = createBoard(newUser1);
        BoardUser boardUser1to1 = createBoardUser(newUser1, board1);
        Board board2 = createBoard(newUser2);
        BoardUser boardUser2to2 = createBoardUser(newUser2, board2);

        /**
         * newUser1: 본인 1(1번), 초대 1(2번)
         * newUser2: 본인 1(2번), 초대 1(1번)
         */
        /* 보드에 사용자 초대 */
        boardService.inviteBoard(board1.getId(), newUser2.getEmail(), board1.getUser()); // 초대할 보드, 초대할 사용자, 초대하는 사용자
        boardService.inviteBoard(board2.getId(), newUser1.getEmail(), board2.getUser()); // 초대할 보드, 초대할 사용자, 초대하는 사용자

        // when
        /* 보드 삭제 */
        boardService.deleteBoard(board1.getId(), newUser1);
        newUser1.removeBoard(board1);
        newUser2.removeBoard(board1);

        boardService.deleteBoard(board2.getId(), newUser2);
        newUser1.removeBoard(board2);
        newUser2.removeBoard(board2);

        boardUser1to1 = boardUserRepository.findByUserAndBoard(newUser1, board1).orElse(null);
        boardUser2to2 = boardUserRepository.findByUserAndBoard(newUser2, board2).orElse(null);

        // then
        /* 1. boardUser 테이블에서 지워졌는지 확인 */
        assertNull(boardUser1to1);
        assertNull(boardUser2to2);

        /* 2. User 테이블에서 지워졌는지 확인 */
        assertEquals(0, newUser1.getBoardList().size());
        assertEquals(0, newUser2.getBoardList().size());
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