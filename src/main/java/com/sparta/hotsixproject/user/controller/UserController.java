package com.sparta.hotsixproject.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.common.jwt.JwtUtil;
import com.sparta.hotsixproject.common.security.UserDetailsImpl;
import com.sparta.hotsixproject.user.dto.*;
import com.sparta.hotsixproject.user.entity.User;
import com.sparta.hotsixproject.user.service.GoolgeService;
import com.sparta.hotsixproject.user.service.KakaoService;
import com.sparta.hotsixproject.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
@Tag(name = "사용자 관련 API", description = "사용자 관련 API 입니다.")
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

    @GetMapping("/profile")
    public String UserProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        return "profile";
    }

    @ResponseBody
    @GetMapping("/user-info")
    @Operation(summary = "사용자 정보 조회", description = "선택한 사용자의 정보를 가져옵니다.")
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserInfoDto userInfoDto = userService.getUserInfo(userDetails.getUser());
        return userInfoDto;
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
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
    @Operation(summary = "닉네임 수정", description = "선택한 사용자의 닉네임을 변경합니다.")
    public ResponseEntity<ApiResponseDto> updateNickname(
            @Parameter(name = "userId", description = "닉네임을 변경할 user의 id", in = ParameterIn.PATH) @PathVariable Long userId,
            @Parameter(description = "변경할 닉네임 정보") @RequestBody UpdateNicknameRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        userService.updateNickname(userId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("닉네임 변경이 완료되었습니다.", HttpStatus.OK.value()));
    }

    @ResponseBody
    @PutMapping("/{userId}/password")
    @Operation(summary = "비밀번호 수정", description = "선택한 사용자의 비밀번호를 변경합니다.")
    public ResponseEntity<ApiResponseDto> updatePassword(
            @Parameter(name = "userId", description = "비밀번호를 변경할 user의 id", in = ParameterIn.PATH) @PathVariable Long userId,
            @Parameter(description = "비밀번호 변경 시 요구되는 사용자의 정보 (password)") @RequestBody UpdatePasswordRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        userService.updatePassword(userId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("비밀번호 변경이 완료되었습니다.", HttpStatus.OK.value()));
    }

    @DeleteMapping("/{userId}/sign-out")
    @Operation(summary = "사용자 탈퇴", description = "선택한 사용자를 탈퇴시킵니다. 현재는 사용자가 스스로 탈퇴할 때 사용합니다.")
    public ResponseEntity<ApiResponseDto> deleteUser(
            @Parameter(name = "userId", description = "탈퇴할(탈퇴시킬) user의 id", in = ParameterIn.PATH) @PathVariable Long userId,
            @Parameter(description = "삭제 시 요구되는 사용자의 정보 (password)") @RequestBody DeleteUserRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        userService.deleteUser(userId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("유저가 탈퇴 되었습니다.", HttpStatus.OK.value()));

    }

    //인가 코드 받아오기 위한 controller
    @GetMapping("/login/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException, UnsupportedEncodingException {
        String token = kakaoService.kakaoLogin(code);
        // Cookie Value 에는 공백이 불가능해서 encoding 진행
        token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20");
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/";
    }

    @GetMapping("/login/google/callback")
    public String googleLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException, UnsupportedEncodingException {
        String token = googleLogin.googleLogin(code);
        // Cookie Value 에는 공백이 불가능해서 encoding 진행
        token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20");
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/";
    }
}
