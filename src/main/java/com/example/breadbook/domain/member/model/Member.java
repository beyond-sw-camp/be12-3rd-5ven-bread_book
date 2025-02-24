package com.example.breadbook.domain.member.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert // 값이 없을 경우 아예 insert 문에 넣지 않음
@Entity
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column(nullable = false, unique = true)
    private String userid;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private String nickname;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    @ColumnDefault(value = "false")
    private Boolean isAdmin;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @ColumnDefault(value = "false")
    private Boolean isDeleted;
    private LocalDateTime deletedAt;
    @ColumnDefault(value = "true")
    private Boolean agree;
    @ColumnDefault(value = "0")
    private Integer score;

    @ColumnDefault(value = "/defaultProfileImg.jpg")
    private String imgUrl;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
//        GrantedAuthority authority = new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return role;
//            }
//        };

        authorities.add(authority);
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public enum GenderType {
        male, female, other
    }
}
