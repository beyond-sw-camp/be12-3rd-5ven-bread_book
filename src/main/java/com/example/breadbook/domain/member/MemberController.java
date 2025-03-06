package com.example.breadbook.domain.member;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.member.model.MemberDto;
import com.example.breadbook.domain.member.service.MemberService;
import com.example.breadbook.global.response.BaseResponse;
import com.example.breadbook.global.utils.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Tag(name = "회원 기능")
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberDto.SignupResponse> signup(@RequestBody MemberDto.SignupRequest dto) {
        return ResponseEntity.ok(memberService.signup(dto));
    }

    @PostMapping("/signup_oauth")
    public ResponseEntity<MemberDto.SignupResponse> signupOauth(@CookieValue(name = "SUTOKEN") String token,
                                                                @RequestBody MemberDto.SignupOauthRequest dto,
                                                                HttpServletResponse response) {

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

    @PostMapping("/modify")
    public ResponseEntity<BaseResponse<MemberDto.MemberInfoResponse>> modify(
            @AuthenticationPrincipal Member member,
            @RequestPart MemberDto.MemberModifyRequest dto,
            @RequestPart(required = false) MultipartFile file) {
        BaseResponse<MemberDto.MemberInfoResponse> response = memberService.modifyMember(member, dto, file);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/info")
    public BaseResponse<MemberDto.MemberInfoResponse> info(@AuthenticationPrincipal Member member) {
        return memberService.memberInfo(member);
    }

    @PostMapping("/id_info")
    public BaseResponse<MemberDto.IdResponse> idInfo(@RequestBody MemberDto.IdRequest dto) {
        return memberService.getId(dto);
    }

    @PostMapping("/password/find")
    public BaseResponse<String> findPassword(@RequestBody MemberDto.PasswordFindRequest dto) {
        return memberService.forgetPassword(dto);
    }

    @PostMapping("/password/reset")
    public BaseResponse<String> resetPassword(@RequestBody MemberDto.PasswordResetRequest dto,
                                              @CookieValue(name = "ATOKEN", required = false) String token) {
        return memberService.resetPassword(dto, token);
    }

    @GetMapping("/auth/check")
    public ResponseEntity<BaseResponse<MemberDto.LoginResponse>> logincheck(
            @CookieValue(name = "ATOKEN", required = false) String token,
            HttpServletResponse httpResponse
    ) throws JsonProcessingException {

        BaseResponse<MemberDto.LoginResponse> response = memberService.logincheck(token);
        if(token != null) {
            String newToken = JwtUtil.refreshToken(token);
            ResponseCookie cookie = ResponseCookie
                    .from("ATOKEN", newToken)
                    .path("/")
                    .httpOnly(true)
                    .secure(true)
                    .maxAge(Duration.ofHours(1L))
                    .build();
            httpResponse.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        }
        String jsonResponse = new ObjectMapper().writeValueAsString(response);
        return ResponseEntity
                .ok()
                .header("Content-Length", String.valueOf(jsonResponse.getBytes(StandardCharsets.UTF_8).length))
                .body(response);
    }

    @GetMapping("/verify")
    public BaseResponse<String> verify(String uuid) {
        return memberService.verify(uuid);
    }

}
