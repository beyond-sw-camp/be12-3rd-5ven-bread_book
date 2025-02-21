package com.example.breadbook.domain.member;

import com.example.breadbook.domain.member.model.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberDto.SignupResponse> signup(@RequestBody MemberDto.SignupRequest dto) {
        return ResponseEntity.ok(memberService.signup(dto));
    }
}
