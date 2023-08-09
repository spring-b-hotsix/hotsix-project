package com.sparta.hotsixproject.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.hotsixproject.common.jwt.JwtUtil;
import com.sparta.hotsixproject.user.UserRoleEnum;
import com.sparta.hotsixproject.user.dto.GoogleUserInfoDto;
import com.sparta.hotsixproject.user.dto.KakaoUserInfoDto;
import com.sparta.hotsixproject.user.entity.User;
import com.sparta.hotsixproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Slf4j(topic = "Goolge Login")
@Service
@RequiredArgsConstructor
public class GoolgeService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    public String googleLogin(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getToken(code);

        // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기

        GoogleUserInfoDto googleUserInfo =getGoogleoUserInfo(accessToken);

        // 3. 필요시에 회원가입
        User googleUser=registerGoogleUserIfNeeded(googleUserInfo);

        // 4. JWT 토큰 반환
        String createToken = jwtUtil.createToken(googleUser.getNickname(), googleUser.getRole());
        return createToken;

    }

    private String getToken(String code) throws JsonProcessingException {
        log.info("인가코드: "+code);
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com")
                .path("/oauth/token")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "676623f0cb1ea82bda1fa48588b0109e");
        body.add("redirect_uri", "http://localhost:8080/users/login/kakao/callback");
        body.add("code", code);

        log.info("마디"+body);
        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(body);

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        return jsonNode.get("access_token").asText();
    }

    private GoogleUserInfoDto getGoogleoUserInfo(String accessToken) throws JsonProcessingException {
        log.info("accessToken: "+accessToken);
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com")
                .path("/v2/user/me")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>());

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
        return new GoogleUserInfoDto(id, nickname, email);
    }

    private User registerGoogleUserIfNeeded(GoogleUserInfoDto googleUserInfo) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        Long googleId = googleUserInfo.getId();
        User googleUser = userRepository.findByGoogleId(googleId).orElse(null);

        if (googleUser == null) {
            // 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
            String kakaoEmail = googleUserInfo.getEmail();
            User sameEmailUser = userRepository.findByEmail(kakaoEmail).orElse(null);
            if (sameEmailUser != null) {
                googleUser = sameEmailUser;
                // 기존 회원정보에 카카오 Id 추가
                googleUser = googleUser.kakaoIdUpdate(googleId);
            } else {
                // 신규 회원가입
                // password: random UUID
                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);

                // email: kakao email
                String email = googleUserInfo.getEmail();

                googleUser = new User(googleUserInfo.getNickname(), encodedPassword, email, UserRoleEnum.USER,null, googleId);
            }

            userRepository.save(googleUser);
        }
        return googleUser;
    }
}
