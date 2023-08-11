package com.sparta.hotsixproject.user.service;

import com.sparta.hotsixproject.user.UserRoleEnum;
import com.sparta.hotsixproject.user.dto.LoginRequestDto;
import com.sparta.hotsixproject.user.entity.User;
import com.sparta.hotsixproject.user.repository.UserRepository;
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
import static org.junit.jupiter.api.Assertions.assertNull;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class UserServiceTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원가입 성공")
    void signupSuccess() {
        // given
        String nickname = "test1";
        String email = "test1234@email.com";
        String encodePw = passwordEncoder.encode("Password@1234");
        LoginRequestDto requestDto = new LoginRequestDto(nickname, encodePw, email);
        userService.signup(requestDto);

        // when
        User newUser1 = userRepository.findByEmail(email).orElse(null);

        // then
        assertEquals(nickname, newUser1.getNickname());
    }

    @Test
    @DisplayName("회원가입 실패")
    void signupFail() throws Exception {
        // given
        String nickname = "test1";
        String email = "test1234@email.com";
        String encodePw = passwordEncoder.encode("Password@1234");
        LoginRequestDto requestDto1 = new LoginRequestDto(nickname, email, encodePw);
        userService.signup(requestDto1);

        nickname = "test2";
        /* email = 이미 가입한 email 사용 */
        encodePw = passwordEncoder.encode("Password@1234");
        LoginRequestDto requestDto2 = new LoginRequestDto(nickname, email, encodePw);
        userService.signup(requestDto2);

        // when
        User newUser1 = userRepository.findByEmail(email).orElse(null);
        User newUser2 = userRepository.findByEmail(email).orElse(null);

        // then
        assertEquals(nickname, newUser1.getNickname()); // user1 = 회원가입 완료
        assertNull(newUser2); // user2 = 회원가입 실패
    }

    @Test
    @DisplayName("회원 정보 수정")
    void updateNicknameAndPassword() {

    }
}