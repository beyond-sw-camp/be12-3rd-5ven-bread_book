package com.example.breadbook.domain.member.service;

import com.example.breadbook.domain.member.model.EmailVerify;
import com.example.breadbook.domain.member.model.Member;
import com.example.breadbook.domain.member.model.MemberDto;
import com.example.breadbook.domain.member.model.PasswordReset;
import com.example.breadbook.domain.member.repository.EmailVerifyRepository;
import com.example.breadbook.domain.member.repository.MemberRepository;
import com.example.breadbook.domain.member.repository.PasswordResetRepository;
import com.example.breadbook.global.response.BaseResponse;
import com.example.breadbook.global.response.BaseResponseMessage;
import com.example.breadbook.global.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerifyRepository emailVerifyRepository;
    private final PasswordResetRepository passwordResetRepository;
    private final JavaMailSender mailSender;
    @Value("${project.upload.path}")
    private String defaultUploadPath;

    public void sendVerifyEmail(String uuid, String email) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[가입인증] 책빵 가입 이메일 인증");
        message.setText(
                "책빵 가입을 위해 아래 링크를 통해 인증을 진행해주세요.\n https://www.breaadbook.kro.kr/email_verify/" + uuid
        );

        mailSender.send(message);
    }

    public void sendPasswordReset(String uuid, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[비밀번호 재설정] 책빵 가입 비밀번호 재설정 안내");
        message.setText(
                "아래 링크를 통해 비밀번호 재설정을 진행해주세요.\n https://www.breadbook.kro.kr/change_pw/" + uuid
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
    public BaseResponse<MemberDto.SignupResponse> signup(MemberDto.SignupRequest dto) {
        String uuid = UUID.randomUUID().toString();
        Member member = memberRepository.save(dto.toEntity(passwordEncoder.encode(dto.getPassword())));
        emailVerifyRepository.save(EmailVerify.builder()
                .member(member)
                .uuid(uuid)
                .build());

        // 이메일 전송
        sendVerifyEmail(uuid, dto.getEmail());
        return new BaseResponse<>(BaseResponseMessage.SIGNUP_SUCCESS ,MemberDto.SignupResponse.fromEntity(member));
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

        sendVerifyEmail(uuid, dto.getEmail());
        return MemberDto.SignupResponse.fromEntity(member);
    }

    public BaseResponse<MemberDto.MemberInfoResponse> memberInfo(Member loginedMember) {
        Member member = memberRepository.findById(loginedMember.getIdx()).orElseThrow();
        MemberDto.MemberInfoResponse response = MemberDto.MemberInfoResponse.fromEntity(member);
        return new BaseResponse<>(BaseResponseMessage.MEMBER_INFO_SUCCESS, response);
    }

    public BaseResponse<String> forgetPassword(MemberDto.PasswordFindRequest dto) {
        String uuid = UUID.randomUUID().toString();
        LocalDateTime time = LocalDateTime.now().plusHours(1L);
        Member member = memberRepository.findByUseridAndEmailAndProvider(
                dto.getUserid(), dto.getEmail(), "email"
        ).orElse(null);
        if(member != null) {
            passwordResetRepository.save(PasswordReset.builder().uuid(uuid).member(member).expiryDate(time).build());
            sendPasswordReset(uuid, dto.getEmail());
        }
        return new BaseResponse<>(BaseResponseMessage.FIND_PASSWORD_SUCCESS, "통신 성공");
    }

    public BaseResponse<String> resetPassword(MemberDto.PasswordResetRequest dto, String token) {
        if(token != null) {
            Member tokenMember = JwtUtil.getMember(token);
            if(tokenMember == null) return new BaseResponse<>(BaseResponseMessage.TOKEN_EXPIRED, "실패");
            Member member = memberRepository.findById(tokenMember.getIdx()).orElseThrow();
            if(!passwordEncoder.matches(dto.getOldPassword(), member.getPassword()))
                return new BaseResponse<>(BaseResponseMessage.RESET_PASSWORD_UNMATCHED, "실패");
            memberRepository.save(member.updateMember(Member.builder()
                    .password(passwordEncoder.encode(dto.getNewPassword()))
                    .build()));
            return new BaseResponse<>(BaseResponseMessage.RESET_PASSWORD_SUCCESS, "비밀번호 변경 성공");
        } else {
            PasswordReset passwordReset = passwordResetRepository
                                            .findByUuidAndExpiryDateAfter(dto.getUuid(), LocalDateTime.now())
                                            .orElse(null);
            if(passwordReset == null) {
                return new BaseResponse<>(BaseResponseMessage.RESET_PASSWORD_NULL, "인증 실패");
            }

            Member member = passwordReset.getMember();
            memberRepository.save(member.updateMember(Member.builder()
                    .password(passwordEncoder.encode(dto.getNewPassword()))
                    .build()));
            return new BaseResponse<>(BaseResponseMessage.RESET_PASSWORD_SUCCESS, "비밀번호 변경 성공");
        }
    }

    @Transactional
    public BaseResponse<MemberDto.MemberInfoResponse> modifyMember(Member loginedMember,
                                                                   MemberDto.MemberModifyRequest dto,
                                                                   MultipartFile file) {
        String uploadFilePath = null;
        if(file!=null && !file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();

            uploadFilePath = "/"+ UUID.randomUUID().toString()+"_"+originalFilename;
            File uploadFile = new File(defaultUploadPath+"/"+uploadFilePath);
            try {
                file.transferTo(uploadFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Member member = memberRepository.findById(loginedMember.getIdx()).orElseThrow();
        member = member.updateMember(dto.toEntity(uploadFilePath));
        member = memberRepository.save(member);

        return new BaseResponse<>(BaseResponseMessage.INFO_MODIFY_SUCCESS,
                MemberDto.MemberInfoResponse.fromEntity(member));

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> result = memberRepository.findByUseridAndProvider(username, "email");
        return result.orElseThrow();
    }

    public BaseResponse<MemberDto.LoginResponse> logincheck(String token) {
        Boolean valid = JwtUtil.validate(token);
        MemberDto.LoginResponse response = null;
        if(valid) {
            response = MemberDto.LoginResponse.fromEntity(JwtUtil.getMember(token));
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
