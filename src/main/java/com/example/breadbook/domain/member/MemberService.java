package com.example.breadbook.domain.member;

import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.member.model.MemberDto;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> result = memberRepository.findByUserid(username);
        return result.orElse(null);
    }
}
