package com.example.breadbook.domain.member;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.member.model.MemberDto;
import com.example.breadbook.global.response.BaseResponse;
import com.example.breadbook.global.response.BaseResponseMessage;
import com.example.breadbook.global.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberDto.SignupResponse signup(MemberDto.SignupRequest dto) {
        Member member = memberRepository.save(dto.toEntity(passwordEncoder.encode(dto.getPassword())));
        Member member2 = memberRepository.findById(member.getIdx()).orElseThrow();
        return MemberDto.SignupResponse.fromEntity(member2);
    }

    public MemberDto.SignupResponse signupOauth(String token, MemberDto.SignupOauthRequest dto) {
        if(!JwtUtil.validate(token)) {
            return null;
        }
        Member temp = JwtUtil.getMember(token);
        Member member = memberRepository.save(dto.toEntity(temp.getUserid(), temp.getProvider()));
        return MemberDto.SignupResponse.fromEntity(member);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> result = memberRepository.findByUseridAndProvider(username, "email");
        return result.orElse(null);
    }

    public BaseResponse<MemberDto.SignupResponse> logincheck(String token) {
        Boolean valid = JwtUtil.validate(token);
        MemberDto.SignupResponse response = null;
        if(valid) {
            response = MemberDto.SignupResponse.fromEntity(JwtUtil.getMember(token));
            return new BaseResponse<>(BaseResponseMessage.LOGIN_SUCCESS, response);
        }
        return new BaseResponse<>(BaseResponseMessage.LOGIN_UNAUTHORIZED, response);
    }
}
