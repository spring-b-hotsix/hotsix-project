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
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
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
    void 컬럼_이름_변경() {
        // given
        User user = createUser();
        Board board = createBoard(user);
        Side side = createSide(board, 1);

        // when
        SideRequestDto requestDto = new SideRequestDto();
        requestDto.setName("side1 name edit");
        sideService.updateSideName(board.getId(), side.getId(), requestDto, user);

        // then
        assertEquals(requestDto.getName(), side.getName()); // 순서대로 expected, actual
    }

    @Test
    void 같은_보드_내에서_컬럼_이동() {
    }

    @Test
    void 컬럼_삭제() {
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