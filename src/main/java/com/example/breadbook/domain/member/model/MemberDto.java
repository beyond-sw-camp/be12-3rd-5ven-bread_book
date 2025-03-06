package com.example.breadbook.domain.member.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MemberDto {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "회원가입 요청 DTO")
    public static class SignupRequest {
        @Schema(description = "회원 ID", example = "test01")
        private String userId;
        @Schema(description = "비밀번호", example = "qwer1234")
        private String password;
        @Schema(description = "회원이름", example = "John Doe")
        private String userName;
        @Schema(description = "인증 가능한 이메일", example = "bread.book.com@gmail.com")
        private String email;
        @Schema(description = "별명", example = "nickname01")
        private String nickName;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @Schema(description = "생년월일", example = "1997-06-15")
        private LocalDate birthDate;
        @Schema(description = "성별", example = "female")
        private Member.GenderType gender;

        public Member toEntity(String encodedPassword) {
            return Member.builder()
                    .userid(userId)
                    .password(encodedPassword)
                    .username(userName)
                    .email(email)
                    .nickname(nickName)
                    .birthDate(birthDate)
                    .gender(gender)
                    .provider("email")
                    .build();
        }
    }


    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    @Schema(description = "Oauth 회원가입 용 DTO")
    public static class SignupOauthRequest {
        @Schema(description = "회원이름", example = "John Doe")
        private String userName;
        @Schema(description = "인증 가능한 이메일", example = "bread.book.com@gmail.com")
        private String email;
        @Schema(description = "별명", example = "nickname01")
        private String nickName;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @Schema(description = "생년월일", example = "1997-06-15")
        private LocalDate birthDate;
        @Schema(description = "성별", example = "female")
        private Member.GenderType gender;
        public Member toEntity(String userId, String provider) {
            return Member.builder()
                    .userid(userId)
                    .password(provider)
                    .username(userName)
                    .email(email)
                    .nickname(nickName)
                    .birthDate(birthDate)
                    .gender(gender)
                    .provider(provider)
                    .build();
        }
    }


    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    @Schema(description = "회원가입 응답 DTO")
    public static class SignupResponse {
        @Schema(description = "회원 고유 idx", example = "1")
        private Long idx;
        @Schema(description = "회원 ID", example = "test01")
        private String userid;
        @Schema(description = "회원이름", example = "John Doe")
        private String username;
        @Schema(description = "이메일", example = "bread.book.com@gmail.com")
        private String email;
        @Schema(description = "별명", example = "nickname01")
        private String nickname;
        @Schema(description = "생년월일", example = "1997-06-15")
        private LocalDate birthDate;
        @Schema(description = "성별", example = "female")
        private Member.GenderType gender;
        @Schema(description = "회원 가입일시", example = "2025-03-06T14:13:04.703Z")
        private LocalDateTime createdAt;
        public static SignupResponse fromEntity(Member member) {
            return SignupResponse.builder()
                    .idx(member.getIdx())
                    .userid(member.getUserid())
                    .username(member.getUsername())
                    .email(member.getEmail())
                    .nickname(member.getNickname())
                    .birthDate(member.getBirthDate())
                    .gender(member.getGender())
                    .createdAt(member.getCreatedAt())
                    .build();
        }
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    @Schema(description = "로그인 확인 응답 DTO")
    public static class LoginResponse {
        @Schema(description = "회원 고유 idx", example = "1")
        private Long idx;
        @Schema(description = "회원 ID", example = "test01")
        private String userid;
        @Schema(description = "회원이름", example = "John Doe")
        private String username;
        @Schema(description = "이메일", example = "bread.book.com@gmail.com")
        private String email;
        @Schema(description = "별명", example = "nickname01")
        private String nickname;
        @Schema(description = "회원 가입 방식", example = "google")
        private String provider;
        public static LoginResponse fromEntity(Member member) {
            return LoginResponse.builder()
                    .idx(member.getIdx())
                    .userid(member.getUserid())
                    .username(member.getUsername())
                    .email(member.getEmail())
                    .nickname(member.getNickname())
                    .provider(member.getProvider())
                    .build();
        }
    }

    @Getter
    @Schema(description = "아이디 찾기 요청 DTO")
    public static class IdRequest {
        @Schema(description = "이름", example = "John Doe")
        private String username;
        @Schema(description = "이메일", example = "bread.book.com@gmail.com")
        private String email;
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    @Schema(description = "ID 찾기 응답 DTO")
    public static class IdResponse {
        @Schema(description = "회원 ID", example = "test01")
        private String userid;
        public static IdResponse fromEntity(Member member) {
            return IdResponse.builder().userid(member.getUserid()).build();
        }
    }

    @Getter
    @Schema(description = "비밀번호 찾기 요청 DTO")
    public static class PasswordFindRequest {
        @Schema(description = "회원 ID", example = "test01")
        private String userid;
        @Schema(description = "이메일", example = "bread.book.com@gmail.com")
        private String email;
    }

    @Getter
    @Schema(description = "비밀번호 재설정 요청 DTO")
    public static class PasswordResetRequest {
        @Schema(description = "비밀번호 재설정 회원을 특정하기 위한 uuid", example = "6a7b31fb-79cf-4089-a417-2dfea2c54829")
        private String uuid;
        @Schema(description = "이전 비밀번호", example = "qwer1234")
        private String oldPassword;
        @Schema(description = "새 비밀번호", example = "uiop7890")
        private String newPassword;
    }


    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    @Schema(description = "회원정보 수정 요청 DTO")
    public static class MemberModifyRequest {
        @Schema(description = "별명", example = "nickname01")
        private String nickname;
        @Schema(description = "생년월일", example = "1997-06-15")
        private LocalDate birthDate;
        @Schema(description = "성별", example = "female")
        private Member.GenderType gender;
        public Member toEntity(String profileImgUrl) {
            return Member.builder()
                    .nickname(nickname)
                    .birthDate(birthDate)
                    .gender(gender)
                    .profileImgUrl(profileImgUrl)
                    .build();
        }
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    @Schema(description = "회원 정보 수정 페이지에 필요한 회원 정보 응답 DTO")
    public static class MemberInfoResponse {
        @Schema(description = "회원 고유 idx", example = "1")
        private Long idx;
        @Schema(description = "회원 ID", example = "test01")
        private String userid;
        @Schema(description = "회원이름", example = "John Doe")
        private String username;
        @Schema(description = "이메일", example = "bread.book.com@gmail.com")
        private String email;
        @Schema(description = "별명", example = "nickname01")
        private String nickname;
        @Schema(description = "생년월일", example = "1997-06-15")
        private LocalDate birthDate;
        @Schema(description = "성별", example = "female")
        private Member.GenderType gender;
        @Schema(description = "프로필 이미지 url", example = "/defaultImage.png")
        private String profileImgUrl;
        public static MemberInfoResponse fromEntity(Member member) {
            return MemberInfoResponse.builder()
                    .idx(member.getIdx())
                    .userid(member.getUserid())
                    .username(member.getUsername())
                    .email(member.getEmail())
                    .nickname(member.getNickname())
                    .birthDate(member.getBirthDate())
                    .gender(member.getGender())
                    .profileImgUrl(member.getProfileImgUrl())
                    .build();
        }
    }

}
