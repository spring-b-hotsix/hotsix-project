package com.sparta.hotsixproject.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.common.jwt.JwtUtil;
import com.sparta.hotsixproject.common.security.UserDetailsImpl;
import com.sparta.hotsixproject.user.dto.*;
import com.sparta.hotsixproject.user.service.GoolgeService;
import com.sparta.hotsixproject.user.service.KakaoService;
import com.sparta.hotsixproject.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final MessageSource messageSource;
    private final KakaoService kakaoService;
    private final GoolgeService googleLogin;

    @GetMapping("/login-page")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/signup-page")
    public String signup() {
        return "signup";
    }

    @ResponseBody
    @GetMapping("/user-info")
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserInfoDto userInfoDto = userService.getUserInfo(userDetails.getUser());
        return userInfoDto;
    }

    @PostMapping("/signup")
    public String signup(@Valid @RequestBody LoginRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
            throw new IllegalArgumentException(
                    messageSource.getMessage(
                            "not.signup.form",
                            null,
                            "Not Signup Form",
                            Locale.getDefault()
                    ));
        }
        userService.signup(requestDto);
        return "redirect:/users/login-page";
    }

    @ResponseBody
    @PutMapping("/{userId}/nickname")
    public ResponseEntity<ApiResponseDto> updateNickname(@PathVariable Long userId, @RequestBody UpdateNicknameRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.updateNicknmae(userId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("닉네임 변경이 완료되었습니다.", HttpStatus.OK.value()));
    }

    @ResponseBody
    @PutMapping("/{userId}/password")
    public ResponseEntity<ApiResponseDto> updatePassword(@PathVariable Long userId, @RequestBody UpdatePasswordRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.updatePassword(userId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("비밀번호 변경이 완료되었습니다.", HttpStatus.OK.value()));
    }

    @DeleteMapping("/{userId}/sign-out")
    public ResponseEntity<ApiResponseDto> deleteUser(@PathVariable Long userId, @RequestBody DeleteUserRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        userService.deleteUser(userId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("유저가 탈퇴 되었습니다.", HttpStatus.OK.value()));

    }

    //인가 코드 받아오기 위한 controller
    @GetMapping("/login/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        String token = kakaoService.kakaoLogin(code);

        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/";
    }

    @GetMapping("/login/google/callback")
    public String googleLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        String token = googleLogin.googleLogin(code);

        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/";
    }
}
