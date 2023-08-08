package com.sparta.hotsixproject.user.service;

import com.sparta.hotsixproject.user.UserRoleEnum;
import com.sparta.hotsixproject.user.dto.LoginRequestDto;
import com.sparta.hotsixproject.user.entity.User;
import com.sparta.hotsixproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public void signup(LoginRequestDto requestDto) {
        String username = requestDto.getNickname();
        String password = passwordEncoder.encode(requestDto.getPassword()); // 패스워드 평문 암호화
        String email = requestDto.getEmail();

        UserRoleEnum role = UserRoleEnum.USER;

        // 사용자 등록
        User user = new User(username, password,email, role);
        userRepository.save(user);
    }
}
