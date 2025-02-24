package com.example.breadbook.global.utils;

import com.example.breadbook.domain.member.model.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    private static String SECRET;
    @Value("${jwt.expired}")
    private int exp;
    private static int EXP;

    @PostConstruct
    public void init() {
        SECRET = secret;
        EXP = exp;
    }

    public static Member getMember(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return Member.builder()
                    .idx(claims.get("memberIdx", Long.class))
                    .username(claims.get("memberUserName", String.class))
                    .nickname(claims.get("memberNickName", String.class))
                    .email(claims.get("memberEmail", String.class))
                    .userid(claims.get("memberUserId", String.class))
                    .provider(claims.get("memberProvider", String.class))
                    .build();

        } catch (ExpiredJwtException e) {
            System.out.println("토큰이 만료되었습니다!");
            return null;
        }
    }

    public static String generateToken(Long memberIdx, String memberEmail,
                                       String memberUserId, String memberUserName,
                                       String memberNickName, String memberProvider) {
        Claims claims = Jwts.claims();
        claims.put("memberNickName", memberNickName);
        claims.put("memberEmail", memberEmail);
        claims.put("memberIdx", memberIdx);
        claims.put("memberUserId", memberUserId);
        claims.put("memberUserName", memberUserName);
        claims.put("memberProvider", memberProvider);
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXP))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
        return token;
    }

    public static boolean validate(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("토큰이 만료되었습니다!");
            return false;
        }
        return true;
    }
}
