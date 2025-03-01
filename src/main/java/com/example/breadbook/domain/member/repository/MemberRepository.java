package com.example.breadbook.domain.member.repository;

import com.example.breadbook.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUseridAndProvider(String userid, String provider);
    Optional<Member> findByUsernameAndEmailAndProvider(String username, String email, String provider);
    Optional<Member> findByUseridAndEmailAndProvider(String userid, String email, String provider);
}
