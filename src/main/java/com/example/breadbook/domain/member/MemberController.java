package com.example.breadbook.domain.member;

import com.example.breadbook.domain.member.model.MemberDto;
import com.example.breadbook.global.response.BaseResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberDto.SignupResponse> signup(@RequestBody MemberDto.SignupRequest dto) {
        return ResponseEntity.ok(memberService.signup(dto));
    }

    @PostMapping("/signup-oauth")
    public ResponseEntity<MemberDto.SignupResponse> signupOauth(@CookieValue(name = "SUTOKEN") String token,
                                                                @RequestBody MemberDto.SignupOauthRequest dto,
                                                                HttpServletResponse response) throws IOException {

        MemberDto.SignupResponse respDto = memberService.signupOauth(token, dto);
        ResponseCookie cookie = ResponseCookie
                .from("SUTOKEN", "")
                .path("/")
                .httpOnly(true)
                .secure(true)
                .maxAge(0)
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        if(respDto == null) {
            return ResponseEntity.badRequest().body(respDto);
        }
        return ResponseEntity.ok(respDto);
    }

    @GetMapping("/auth/check")
    public ResponseEntity<BaseResponse<MemberDto.SignupResponse>> logincheck(
            @CookieValue(name = "ATOKEN", required = false) String token) throws JsonProcessingException {

        BaseResponse<MemberDto.SignupResponse> response = memberService.logincheck(token);
        System.out.println(response.getData()+" "+ response.getIsSuccess());
        String jsonResponse = new ObjectMapper().writeValueAsString(response);
        return ResponseEntity
                .ok()
                .header("Content-Length", String.valueOf(jsonResponse.getBytes(StandardCharsets.UTF_8).length))
                .body(response);
    }

}
