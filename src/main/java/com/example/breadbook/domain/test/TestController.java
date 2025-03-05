package com.example.breadbook.domain.test;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.test.model.TestDto;
import com.example.breadbook.global.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cookies")
@Log4j2
public class TestController {
    private final TestService testService;

    @PostMapping("/user")
    public TestDto.tokenUser getTokenIdx( HttpServletRequest request) {
        try{
             return testService.cookiesValue(request);
        }catch (Exception e){
            throw new RuntimeException("JWT 디코딩 중 오류 발생", e);
        }

    }
}
