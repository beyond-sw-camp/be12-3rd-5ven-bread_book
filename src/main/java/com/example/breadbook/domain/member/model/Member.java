package com.example.breadbook.domain.member.model;


import com.example.breadbook.domain.order.model.Order;
import com.example.breadbook.domain.product.model.Product;
import com.example.breadbook.domain.wish.model.Wish;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
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
import java.util.List;

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
    private String provider;
    @ColumnDefault(value = "false")
    private Boolean enabled;

    @ColumnDefault(value = "'/defaultProfileImg.jpg'")
    private String profileImgUrl;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wish> wishList = new ArrayList<>();


    @Builder.Default
    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
    @BatchSize(size = 5)
    private List<Product> products = new ArrayList<>();


    @Builder.Default
    @OneToMany(mappedBy = "member")
    @BatchSize(size = 5)
    private List<Order> orders=new ArrayList<>();

    public void setScore(Integer score) {
        this.score = score;
    }


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

    public void memberVerify() {
        this.enabled = true;
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
        return this.enabled;
    }

    public Member updateMember(Member member) {
        return Member.builder()
                .idx(this.getIdx())
                .userid(this.userid)
                .username(member.getUsername() == null ? this.username : member.getUsername())
                .email(this.email)
                .password(member.getPassword() == null ? this.password : member.getPassword())
                .nickname(member.getNickname() == null ? this.nickname : member.getNickname())
                .birthDate(member.getBirthDate() == null ? this.birthDate : member.getBirthDate())
                .gender(member.getGender() == null ? this.gender : member.getGender())
                .createdAt(this.createdAt)
                .isAdmin(this.isAdmin)
                .isDeleted(this.isDeleted)
                .deletedAt(this.deletedAt)
                .agree(this.agree)
                .score(this.score)
                .provider(this.provider)
                .enabled(this.enabled)
                .profileImgUrl(member.getProfileImgUrl() == null ? this.profileImgUrl : member.getProfileImgUrl())
                .build();
    }


    public enum GenderType {
        male, female, other
    }
}
