package com.example.breadbook.global.handler;

import com.example.breadbook.domain.member.model.CustomOAuth2Member;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.global.utils.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;

public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object object = authentication.getPrincipal();
        CustomOAuth2Member oAuth2User = (CustomOAuth2Member) object;
        oAuth2User.getAttributes().forEach((key, value) -> {
            System.out.println(key + ":" + value);
        });
        oAuth2User.getAuthorities().forEach(System.out::println);
        System.out.println(oAuth2User.getName());
        Member member = oAuth2User.getMember();
        String tokenName;
        String redirectUrl;
        String jwtToken;
        Duration duration;
        if (member.getIdx() == null) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            jwtToken = JwtUtil.generateToken(member.getIdx(), member.getEmail(),
                    member.getUserid(), member.getUsername(),member.getNickname(),
                     member.getProvider());
            tokenName = "SUTOKEN";
            redirectUrl = "/signup_oauth";
            duration = Duration.ofHours(1L);
        } else if(!member.getEnabled()){
            jwtToken = "null";
            tokenName = "NULL";
            redirectUrl = "/verify_fail";
            duration = Duration.ofHours(0L);
        } else {
            jwtToken = JwtUtil.generateToken(member.getIdx(), member.getEmail(),
                    member.getUserid(), member.getUsername(),member.getNickname(),
                    member.getProvider()
            );
            tokenName = "ATOKEN";
            redirectUrl = "/";
            duration = Duration.ofHours(2L);
//        빌더 패턴으로 객체를 생성하면서 값을 설정하는 방법

        }
        ResponseCookie cookie = ResponseCookie
                .from(tokenName, jwtToken)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .maxAge(duration)
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
//        System.out.println(request.getRequestURI());
//        System.out.println(request.getContextPath());
        response.sendRedirect("http://localhost:5173" + redirectUrl); //프론트 엔드의 특정 주소
    }
}
