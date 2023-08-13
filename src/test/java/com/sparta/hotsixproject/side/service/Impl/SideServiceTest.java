package com.sparta.hotsixproject.side.service.Impl;

import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.side.dto.SideMoveDto;
import com.sparta.hotsixproject.side.dto.SideRequestDto;
import com.sparta.hotsixproject.side.entity.Side;
import com.sparta.hotsixproject.side.repository.SideRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class SideServiceTest {
//    @PersistenceContext
//    EntityManager em;
//    @Autowired
//    PasswordEncoder passwordEncoder;
//    @Autowired
//    SideCustomServiceImpl sideService;
//    @Autowired
//    SideRepository sideRepository;
//
//    /**
//     * 테스트 순서 **
//     * 0. 유저 생성 → 보드 생성 → 컬럼(side) 생성
//     * 1. 컬럼 이름 수정 확인
//     * 2. 컬럼 이동 확인
//     * 3. 컬럼 삭제 확인
//     */
//
//    @Test
//    @DisplayName("컬럼 이름 변경")
//    void updateSideName() {
//        // given
//        User user = createUser("nick1", "email1@email.com");
//        Board board = createBoard(user);
//        Side side = createSide(board, 1);
//
//        // when
//        SideRequestDto requestDto = new SideRequestDto("side1 name edit");
//        sideService.updateSideName(board.getId(), side.getId(), requestDto, user);
//
//        // then
//        assertEquals(requestDto.getName(), side.getName()); // 순서대로 expected, actual
//    }
//
//    @Test
//    @DisplayName("같은 보드 내에서 컬럼 이동")
//    void moveSideToSameBoard() {
//        // given
//        User user = createUser("nick1", "email1@email.com");
//        Board board1 = createBoard(user);
//        Side side1 = createSide(board1, 1);
//        Side side2 = createSide(board1, 2);
//        Side side3 = createSide(board1, 3);
//
//        // when 1
//        /** 1번째 이동
//         * 이동할 컬럼: side3
//         * 이동 전 = board:1, index:2, position: 3072
//         * 이동 후 = board:1, index:0, position: (0+1024)/2 = 512?
//         */
//        SideMoveDto requestDto1 = new SideMoveDto(board1.getId(), 0);
//        sideService.moveSide(board1.getId(), side3.getId(), requestDto1, user);
//
//        // 정렬된 컬럼 리스트
//        List<Side> sortedSideList = sideRepository.findAllByBoardIdOrderBySidePositionAsc(board1.getId());
//
//        // then 1
//        /* 포지션 비교 */
//        assertEquals(0, sortedSideList.indexOf(side3)); // index: 0
//        assertEquals(1, sortedSideList.indexOf(side1)); // index: 1
//        assertEquals(2, sortedSideList.indexOf(side2)); // index: 2
//
//        assertEquals(512, side3.getPosition()); // position: 512
//        assertEquals(1024, side1.getPosition()); // position: 1024
//        assertEquals(2048, side2.getPosition()); // position: 2048
//
//        // when 2
//        /** 2번째 이동
//         * 이동할 컬럼: side1
//         * 이동 전 = board:1, index:1, position: 1024
//         * 이동 후 = board:1, index:2, position: (2048+3072)/2 = 2560?
//         */
//        SideMoveDto requestDto2 = new SideMoveDto(board1.getId(), 2);
//        sideService.moveSide(board1.getId(), side1.getId(), requestDto2, user);
//
//        // 정렬된 컬럼 리스트
//        List<Side> sortedSideList2 = sideRepository.findAllByBoardIdOrderBySidePositionAsc(board1.getId());
//
//        // then 2
//        /* 포지션 비교 */
//        assertEquals(512, side3.getPosition()); // position: 512
//        assertEquals(2048, side2.getPosition()); // position: 2048
//        assertEquals(2560, side1.getPosition()); // position: 2560
//
//        assertEquals(0, sortedSideList2.indexOf(side3)); // index: 0
//        assertEquals(1, sortedSideList2.indexOf(side2)); // index: 1
//        assertEquals(2, sortedSideList2.indexOf(side1)); // index: 2
//    }
//
//    @Test
//    @DisplayName("다른 보드로 컬럼 이동")
//    void moveSideToDifferBoard() {
//        // given
//        User user = createUser("nick1", "email1@email.com");
//        Board board1 = createBoard(user);
//        Side side1 = createSide(board1, 1);
//        Side side2 = createSide(board1, 2);
//
//        Board board2 = createBoard(user);
//        Side side3 = createSide(board1, 1);
//        Side side4 = createSide(board2, 2);
//
//        // when 1
//        /** 1번째 이동
//         * 이동할 컬럼: side3
//         * 이동 전 = board:2, index:0, position: 1024
//         * 이동 후 = board:1, index:1, position: (1024+1536)/2 = 1280?
//         */
//        SideMoveDto requestDto1 = new SideMoveDto(board1.getId(), 1);
//        sideService.moveSide(board2.getId(), side3.getId(), requestDto1, user);
//
//        // 정렬된 컬럼 리스트
//        List<Side> sortedSideList11 = sideRepository.findAllByBoardIdOrderBySidePositionAsc(board1.getId());
//        List<Side> sortedSideList12 = sideRepository.findAllByBoardIdOrderBySidePositionAsc(board2.getId());
//
//        // then 1
//        /* 인덱스 비교, 포지션 비교 */
//        assertEquals(0, sortedSideList11.indexOf(side1)); // board: 1, index: 0
//        assertEquals(1, sortedSideList11.indexOf(side2)); // board: 1, index: 1
//        assertEquals(2, sortedSideList11.indexOf(side3)); // board: 1, index: 2
//        assertEquals(0, sortedSideList12.indexOf(side4)); // board: 2, index: 0
//
//        assertEquals(1024, side1.getPosition()); // board: 1, position: 1024
//        assertEquals(2048, side2.getPosition()); // board: 1, position: 2048
//        assertEquals(2560, side3.getPosition()); // board: 1, position: 2560
//        assertEquals(2048, side4.getPosition()); // board: 2, position: 2048
//
//        // when 2
//        /** 2번째 이동
//         * 이동할 컬럼: side2
//         * 이동 전 = board:1, index:2, position: 2048
//         * 이동 후 = board:2, index:0, position: (0+1024)/2 = 512?
//         */
//        SideMoveDto requestDto2 = new SideMoveDto(board2.getId(), 0);
//        sideService.moveSide(board1.getId(), side2.getId(), requestDto2, user);
//
//        // 정렬된 컬럼 리스트
//        List<Side> sortedSideList21 = sideRepository.findAllByBoardIdOrderBySidePositionAsc(board1.getId());
//        List<Side> sortedSideList22 = sideRepository.findAllByBoardIdOrderBySidePositionAsc(board2.getId());
//
//        // then 2
//        /* 인덱스 비교, 포지션 비교 */
//        assertEquals(0, sortedSideList21.indexOf(side1)); // board: 1, index: 0
//        assertEquals(1, sortedSideList21.indexOf(side3)); // board: 1, index: 1
//        assertEquals(0, sortedSideList22.indexOf(side4)); // board: 2, index: 0
//        assertEquals(1, sortedSideList22.indexOf(side2)); // board: 2, index: 1
//
//        assertEquals(1024, side1.getPosition()); // board: 1, position: 1024
//        assertEquals(2560, side3.getPosition()); // board: 1, position: 2560
//        assertEquals(2048, side4.getPosition()); // board: 2, position: 2048
//        assertEquals(2560, side2.getPosition()); // board: 2, position: 2560
//    }
//
//
//    private Side createSide(Board board, int pos) {
//        Side side = new Side("side1", 1024 * pos, board);
//        board.addSide(side);
//        em.persist(side);
//        return side;
//    }
//
//    private Board createBoard(User user) {
//        Board board = new Board("board1", "descr1", user, 255, 255, 255);
//        user.addBoard(board);
//        em.persist(board);
//        return board;
//    }
//
//    private User createUser(String nickname, String email) {
//        String encodePw = passwordEncoder.encode("Password@1234");
//        User user = new User(nickname, encodePw, email, UserRoleEnum.USER);
//        em.persist(user);
//        return user;
//    }
}