package com.example.breadbook.domain.member.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    public static class SignupRequest {
        private String userId;
        private String password;
        private String userName;
        private String email;
        private String nickName;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate birthDate;
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
    public static class SignupOauthRequest {
        private String userName;
        private String email;
        private String nickName;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate birthDate;
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
    public static class SignupResponse {
        private Long idx;
        private String userid;
        private String username;
        private String email;
        private String nickname;
        private LocalDate birthDate;
        private Member.GenderType gender;
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
    public static class LoginResponse {
        private Long idx;
        private String userid;
        private String username;
        private String email;
        private String nickname;
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
    public static class IdRequest {
        private String username;
        private String email;
    }

    @Getter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class IdResponse {
        private String userid;
        public static IdResponse fromEntity(Member member) {
            return IdResponse.builder().userid(member.getUserid()).build();
        }
    }

    @Getter
    public static class PasswordFindRequest {
        private String userid;
        private String email;
    }

    @Getter
    public static class PasswordResetRequest {
        private String uuid;
        private String oldPassword;
        private String newPassword;
    }

}
