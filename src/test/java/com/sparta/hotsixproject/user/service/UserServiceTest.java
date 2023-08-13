package com.sparta.hotsixproject.user.service;

import com.sparta.hotsixproject.user.dto.LoginRequestDto;
import com.sparta.hotsixproject.user.entity.User;
import com.sparta.hotsixproject.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class UserServiceTest {
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
}