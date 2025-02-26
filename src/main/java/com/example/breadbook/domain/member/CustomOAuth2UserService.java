package com.example.breadbook.domain.member;

import com.example.breadbook.domain.member.model.CustomOAuth2Member;
import com.example.breadbook.domain.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        String email = "";
        String userid = "";
        if(provider.equalsIgnoreCase("google")) {
            email = oAuth2User.getAttribute("email");
            userid = email;
        } else if(provider.equalsIgnoreCase("kakao")) {
            email = ((Map)oAuth2User.getAttribute("properties")).get("nickname").toString() + "@kakao.com";
            userid = email;
        } else if(provider.equalsIgnoreCase("naver")) {
            email = ((Map)oAuth2User.getAttribute("response")).get("email").toString();
            userid = ((Map)oAuth2User.getAttribute("response")).get("id").toString();
        }

        // 내 웹에 회원 가입이 안 되어 있으면
        Member member = memberRepository.findByUseridAndProvider(userid, provider).orElse(null);
        if(member == null) {
            member = Member.builder().email(email).userid(userid).provider(provider).build();
        } else {
            member = Member.builder().idx(member.getIdx()).email(member.getEmail()).provider(provider)
                    .score(member.getScore()).userid(member.getUserid())
                    .birthDate(member.getBirthDate()).username(member.getUsername())
                    .nickname(member.getNickname()).gender(member.getGender()).enabled(member.getEnabled())
                    .build();
        }

        return new CustomOAuth2Member(member, oAuth2User.getAttributes());
    }
}
