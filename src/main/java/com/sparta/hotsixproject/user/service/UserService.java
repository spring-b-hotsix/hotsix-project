package com.sparta.hotsixproject.user.service;

import com.sparta.hotsixproject.user.UserRoleEnum;
import com.sparta.hotsixproject.user.dto.*;
import com.sparta.hotsixproject.user.entity.User;
import com.sparta.hotsixproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Transactional
    public void signup(AuthRequestDto requestDto) {
        String nickname = requestDto.getNickname();
        String encodePassword = passwordEncoder.encode(requestDto.getPassword()); // 패스워드 평문 암호화
        String email = requestDto.getEmail();
        UserRoleEnum role = UserRoleEnum.USER;

        // 회원 중복 확인
        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new IllegalArgumentException(
                    messageSource.getMessage(
                            "already.in.username",
                            null,
                            "Already have UserName",
                            Locale.getDefault()
                    )

            );
        }

        // 사용자 이메일 중복 확인
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException(
                    messageSource.getMessage(
                            "already.in.email",
                            null,
                            "Already have Email",
                            Locale.getDefault()
                    ));
        }

        // 사용자 등록
        User user = new User(nickname, encodePassword, email, role);
        userRepository.save(user);
    }

    public UserInfoDto getUserInfo(User user) {
        User newuser = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("회원정보가 없습니다."));
        String nickname = newuser.getNickname();
        return new UserInfoDto(nickname);
    }

    @Transactional
    public void updateNicknmae(Long userId, UpdateNicknameRequestDto requestDto, User user) {

        User newuser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다"));

        if (!newuser.getNickname().equals(user.getNickname())) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // 회원 중복 확인
        if(userRepository.findByNickname(requestDto.getNickname()).isPresent()){
            throw new IllegalArgumentException(
                    messageSource.getMessage(
                            "already.in.username",
                            null,
                            "Already have UserName",
                            Locale.getDefault()
                    )

            );}

        newuser.updateNicknmae(requestDto.getNickname());

    }
    @Transactional
    public void updatePassword(Long userId, UpdatePasswordRequestDto requestDto, User user) {

        User newuser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다"));

        if (!newuser.getNickname().equals(user.getNickname())) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String encodePassword = passwordEncoder.encode(requestDto.getNewPassword()); // 패스워드 평문 암호화

        newuser.updatePassword(encodePassword);


    }
    @Transactional
    public void deleteUser(Long userId, DeleteUserRequestDto requestDto, User user) {
        User newuser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다"));

        if (!newuser.getNickname().equals(user.getNickname())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(newuser);
    }
}
