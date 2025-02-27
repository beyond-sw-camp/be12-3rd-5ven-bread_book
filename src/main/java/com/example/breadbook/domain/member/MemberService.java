package com.example.breadbook.domain.member;

import com.example.breadbook.domain.member.model.EmailVerify;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.member.model.MemberDto;
import com.example.breadbook.global.response.BaseResponse;
import com.example.breadbook.global.response.BaseResponseMessage;
import com.example.breadbook.global.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerifyRepository emailVerifyRepository;
    private final JavaMailSender mailSender;

    public void sendEmail(String uuid, String email) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[가입인증] 책빵 가입 이메일 인증");
        message.setText(
                "책빵 가입을 위해 아래 링크를 통해 인증을 진행해주세요.\n http://localhost:5173/email_verify/" + uuid
        );

        mailSender.send(message);
    }

    public BaseResponse<String> verify(String uuid) {
        EmailVerify emailVerify = emailVerifyRepository.findByUuid(uuid).orElse(null);
        if(emailVerify == null) {
            return new BaseResponse<>(BaseResponseMessage.EMAIL_VERIFY_NULL, "인증 실패");
        }

        Member member = emailVerify.getMember();
        member.memberVerify();

        memberRepository.save(member);
        return new BaseResponse<>(BaseResponseMessage.EMAIL_VERIFY_SUCCESS, "인증 성공");
    }

    @Transactional
    public MemberDto.SignupResponse signup(MemberDto.SignupRequest dto) {
        String uuid = UUID.randomUUID().toString();
        Member member = memberRepository.save(dto.toEntity(passwordEncoder.encode(dto.getPassword())));
        emailVerifyRepository.save(EmailVerify.builder()
                .member(member)
                .uuid(uuid)
                .build());

        // 이메일 전송
        sendEmail(uuid, dto.getEmail());
        return MemberDto.SignupResponse.fromEntity(member);
    }

    @Transactional
    public MemberDto.SignupResponse signupOauth(String token, MemberDto.SignupOauthRequest dto) {
        if(!JwtUtil.validate(token)) {
            return null;
        }
        String uuid = UUID.randomUUID().toString();
        Member temp = JwtUtil.getMember(token);
        Member member = memberRepository.save(dto.toEntity(temp.getUserid(), temp.getProvider()));
        emailVerifyRepository.save(EmailVerify.builder()
                .member(member)
                .uuid(uuid)
                .build());

        sendEmail(uuid, dto.getEmail());
        return MemberDto.SignupResponse.fromEntity(member);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> result = memberRepository.findByUseridAndProvider(username, "email");
        return result.orElseThrow();
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

    public BaseResponse<MemberDto.IdResponse> getId(MemberDto.IdRequest dto) {
        Member member = memberRepository
                .findByUsernameAndEmailAndProvider(dto.getUsername(), dto.getEmail(), "email")
                .orElse(null);
        if(member == null) {
            return new BaseResponse<>(BaseResponseMessage.FIND_ID_NULL, null);
        }
        MemberDto.IdResponse response = MemberDto.IdResponse.fromEntity(member);
        return new BaseResponse<>(BaseResponseMessage.FIND_ID_SUCCESS, response);
    }
}
