package com.example.breadbook.domain.test;

import com.example.breadbook.domain.test.model.TestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Base64;

@RequiredArgsConstructor
@Service
public class TestService {
    private final TestRepository testRepository;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public TestDto.tokenUser cookiesValue(HttpServletRequest request) throws JsonProcessingException {
        // ATOKEN 쿠키를 가져오는 간단한 방법
        String atoken = Arrays.stream(request.getCookies())
                .filter(cookie -> "ATOKEN".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ATOKEN 쿠키가 없습니다."));

        // JWT 형식 검증 및 Payload 추출
        String[] parts = atoken.split("\\.");
        if (parts.length < 2) {
            throw new IllegalArgumentException("잘못된 JWT 형식입니다.");
        }

        String payloadJson = new String(Base64.getDecoder().decode(parts[1]));

        // JSON 파싱하여 필요한 정보 추출 후 DTO 반환
        JsonNode jsonNode = objectMapper.readTree(payloadJson);

        return TestDto.tokenUser.builder()
                .idx(jsonNode.get("memberIdx").asLong())
                .userId(jsonNode.get("memberUserId").asText())
                .username(jsonNode.get("memberUserName").asText())
                .build();
    }
}
