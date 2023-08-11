package com.sparta.hotsixproject.user.service;

import com.sparta.hotsixproject.user.dto.LoginRequestDto;
import com.sparta.hotsixproject.user.dto.UpdateNicknameRequestDto;
import com.sparta.hotsixproject.user.dto.UpdatePasswordRequestDto;
import com.sparta.hotsixproject.user.entity.User;
import com.sparta.hotsixproject.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


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
        String email = "test1@email.com";
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
    void signupFail() throws IllegalArgumentException {
        // given
        String nickname = "test2";
        String email = "test2@email.com";
        String encodePw = passwordEncoder.encode("Password@1234");
        LoginRequestDto requestDto1 = new LoginRequestDto(nickname, encodePw, email);
        userService.signup(requestDto1);

        nickname = "test3";
        /* email = 이미 가입한 email 사용 */
        encodePw = passwordEncoder.encode("Password@1234");
        LoginRequestDto requestDto2 = new LoginRequestDto(nickname, encodePw, email);

        // when
        IllegalArgumentException exception = assertThrows( // 동일한 이메일로 회원가입 시 예외 throw
                IllegalArgumentException.class, () -> {
                    userService.signup(requestDto2);
                }
        );
        User newUser1 = userRepository.findByNickname("test2").orElse(null);
        User newUser2 = userRepository.findByNickname("test3").orElse(null);

        // then
        assertEquals("test2", newUser1.getNickname()); // user1 = 회원가입 완료
        assertEquals("이미 존재하는 이메일입니다.", exception.getMessage()); // user2 가입 시 예외 throw
        assertNull(newUser2); // user2 = 회원가입 실패
    }

    @Test
    @DisplayName("회원 정보 수정")
    void updateNicknameAndPassword() {
        // given
        String nickname = "test4";
        String email = "test4@email.com";
        String encodePw = passwordEncoder.encode("Password@1234");
        LoginRequestDto signupDto = new LoginRequestDto(nickname, encodePw, email);

        userService.signup(signupDto);
        User newUser = userRepository.findByEmail(email).orElse(null);

        // when
        /* 1. 닉네임 변경 */
        nickname = "newnick1";
        UpdateNicknameRequestDto nicknameDto = new UpdateNicknameRequestDto(nickname, signupDto.getPassword());
        userService.updateNickname(newUser.getId(), nicknameDto, newUser);

        /* 2. 비밀번호 변경 */
        encodePw = passwordEncoder.encode("newPass@1234");
        UpdatePasswordRequestDto pwDto = new UpdatePasswordRequestDto(signupDto.getPassword(), encodePw);
        userService.updatePassword(newUser.getId(), pwDto, newUser);

        // then
        /* 1. 닉네임 비교 */
        assertEquals("newnick1", newUser.getNickname());
        /* 2. 비밀번호 비교 */
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean match = encoder.matches(encodePw, newUser.getPassword()); // 비교
        assertTrue(match);
    }
}