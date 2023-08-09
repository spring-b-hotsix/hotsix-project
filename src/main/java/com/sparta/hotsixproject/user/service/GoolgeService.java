package com.sparta.hotsixproject.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.hotsixproject.common.jwt.JwtUtil;
import com.sparta.hotsixproject.user.UserRoleEnum;
import com.sparta.hotsixproject.user.dto.GoogleUserInfoDto;
import com.sparta.hotsixproject.user.entity.User;
import com.sparta.hotsixproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j(topic = "Goolge Login")
@Service
@RequiredArgsConstructor
public class GoolgeService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;
    private final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    private final String GET_GOOGLE_USER = "https://www.googleapis.com/drive/v2/files";

    @Value("${oauth2.google.client-id}")
    private String GOOGLE_CLIENT_ID;

    @Value("${oauth2.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;
    private String grantType = "authorization_code";

    public String googleLogin(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        //String accessToken = getToken(code);
        String infoToken = getToken(code);

        // 2. 토큰으로 카카오 API 호출 : "info_token"으로 "구글 사용자 정보" 가져오기
        GoogleUserInfoDto googleUserInfo = getGoogleoUserInfo(infoToken);

        // 3. 필요시에 회원가입
        User googleUser = registerGoogleUserIfNeeded(googleUserInfo);

        // 4. JWT 토큰 반환
        String createToken = jwtUtil.createToken(googleUser.getNickname(), googleUser.getRole());
        return createToken;

    }

    private String getToken(String code) throws JsonProcessingException {
        log.info("인가코드: " + code);

        Map<String, String> params = new HashMap<>();

        params.put("code", code);
        params.put("client_id", GOOGLE_CLIENT_ID);
        params.put("client_secret", GOOGLE_CLIENT_SECRET);
        params.put("redirect_uri", "http://localhost:8080/users/login/google/callback");
        params.put("grant_type", grantType);


        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_TOKEN_URL, params, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            // HTTP 응답 (JSON) -> 액세스 토큰 파싱
            JsonNode jsonNode = new ObjectMapper().readTree(responseEntity.getBody());
            log.info("jwt" + jsonNode.get("id_token").asText());
            return jsonNode.get("id_token").asText();
        }
        return null;
    }

    private GoogleUserInfoDto getGoogleoUserInfo(String infoToken) throws JsonProcessingException {

        String[] chunks = infoToken.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));

        log.info("decoded string: " + payload);

        JsonNode jsonNode = new ObjectMapper().readTree(payload);

        String id= jsonNode.get("sub").asText();
        String email= jsonNode.get("email").asText();
        String name= jsonNode.get("name").asText();

        return new GoogleUserInfoDto(id,name,email);
    }

    private User registerGoogleUserIfNeeded(GoogleUserInfoDto googleUserInfo) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        String googleId = googleUserInfo.getId();
        User googleUser = userRepository.findByGoogleId(googleId).orElse(null);

        if (googleUser == null) {
            // 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
            String googleEmail = googleUserInfo.getEmail();
            User sameEmailUser = userRepository.findByEmail(googleEmail).orElse(null);
            if (sameEmailUser != null) {
                googleUser = sameEmailUser;
                // 기존 회원정보에 카카오 Id 추가
                googleUser = googleUser.googleIdUpdate(googleId);
            } else {
                // 신규 회원가입
                // password: random UUID
                String password = UUID.randomUUID().toString();
                String encodedPassword = passwordEncoder.encode(password);

                // email: kakao email
                String email = googleUserInfo.getEmail();

                googleUser = new User(googleUserInfo.getNickname(), encodedPassword, email, UserRoleEnum.USER, null, googleId);
            }

            userRepository.save(googleUser);
        }
        return googleUser;
    }
}
