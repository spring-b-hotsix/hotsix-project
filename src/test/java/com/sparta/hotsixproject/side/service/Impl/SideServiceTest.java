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
    @PersistenceContext
    EntityManager em;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    SideCustomServiceImpl sideService;
    @Autowired
    SideRepository sideRepository;

    /**
     * 테스트 순서 **
     * 0. 유저 생성 → 보드 생성 → 컬럼(side) 생성
     * 1. 컬럼 이름 수정 확인
     * 2. 컬럼 이동 확인
     * 3. 컬럼 삭제 확인
     */

    @Test
    @DisplayName("컬럼 이름 변경")
    @Transactional
    void updateSideName() {
        // given
        User user = createUser();
        Board board = createBoard(user);
        Side side = createSide(board, 1);

        // when
        SideRequestDto requestDto = new SideRequestDto("side1 name edit");
        sideService.updateSideName(board.getId(), side.getId(), requestDto, user);

        // then
        assertEquals(requestDto.getName(), side.getName()); // 순서대로 expected, actual
    }

    @Test
    @DisplayName("같은 보드 내에서 컬럼 이동")
    void moveSideToSameBoard() {
        // given
        User user = createUser();
        Board board1 = createBoard(user);
        Side side1 = createSide(board1, 1);
        board1.addSide(side1);
        Side side2 = createSide(board1, 2);
        board1.addSide(side2);
        Side side3 = createSide(board1, 3);
        board1.addSide(side3);

        // when 1
        /** 1번째 이동
         * 이동할 컬럼: side3
         * 이동 전 = board:1, index:2, position: 3072
         * 이동 후 = board:1, index:0, position: (0+1024)/2 = 512?
         */
        SideMoveDto requestDto1 = new SideMoveDto(board1.getId(), 0);
        sideService.moveSide(board1.getId(), side3.getId(), requestDto1, user);

        // 정렬된 컬럼 리스트
        List<Side> sortedSideList = sideRepository.findAllByBoardIdOrderBySidePositionAsc(board1.getId());

        // then 1
        /* 포지션 비교 */
        assertEquals(0, sortedSideList.indexOf(side3)); // index: 0
        assertEquals(1, sortedSideList.indexOf(side1)); // index: 1
        assertEquals(2, sortedSideList.indexOf(side2)); // index: 2

        assertEquals(512, side3.getPosition()); // position: 512
        assertEquals(1024, side1.getPosition()); // position: 1024
        assertEquals(2048, side2.getPosition()); // position: 2048

        // when 2
        /** 2번째 이동
         * 이동할 컬럼: side1
         * 이동 전 = board:1, index:1, position: 1024
         * 이동 후 = board:1, index:2, position: (2048+3072)/2 = 2560?
         */
        SideMoveDto requestDto2 = new SideMoveDto(board1.getId(), 2);
        sideService.moveSide(board1.getId(), side1.getId(), requestDto2, user);

        // 정렬된 컬럼 리스트
        List<Side> sortedSideList2 = sideRepository.findAllByBoardIdOrderBySidePositionAsc(board1.getId());

        // then 2
        /* 포지션 비교 */
        assertEquals(512, side3.getPosition()); // position: 512
        assertEquals(2048, side2.getPosition()); // position: 2048
        assertEquals(2560, side1.getPosition()); // position: 2560

        assertEquals(0, sortedSideList2.indexOf(side3)); // index: 0
        assertEquals(1, sortedSideList2.indexOf(side2)); // index: 1
        assertEquals(2, sortedSideList2.indexOf(side1)); // index: 2
    }

    @Test
    @DisplayName("다른 보드로 컬럼 이동")
    void moveSideToDifferBoard() {

    }



    private Side createSide(Board board, int pos) {
        Side side = new Side("side1", 1024 * pos, board);
        em.persist(side);
        return side;
    }

    private Board createBoard(User user) {
        Board board = new Board("board1", "descr1", user, 255, 255, 255);
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