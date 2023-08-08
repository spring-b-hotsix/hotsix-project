package com.sparta.hotsixproject.user.controller;

import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.user.dto.LoginRequestDto;
import com.sparta.hotsixproject.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/users/signup")
    public ResponseEntity<ApiResponseDto> signup(@Valid @RequestBody LoginRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
            throw new IllegalArgumentException("바인딩 에러");
        }
        userService.signup(requestDto);
        return ResponseEntity.ok(new ApiResponseDto("회원가입 완료", HttpStatus.OK.value()));
    }
}
