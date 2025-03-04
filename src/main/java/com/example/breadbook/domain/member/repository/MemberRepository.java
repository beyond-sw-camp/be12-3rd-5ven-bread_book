package com.example.breadbook.domain.member.repository;

import com.example.breadbook.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUseridAndProvider(String userid, String provider);
    Optional<Member> findByUsernameAndEmailAndProvider(String username, String email, String provider);
    Optional<Member> findByUseridAndEmailAndProvider(String userid, String email, String provider);

    @Query("SELECT m FROM Member m " +
            "LEFT JOIN FETCH m.orders o " +  //N
            "LEFT JOIN FETCH o.product p " +  //N
            "LEFT JOIN FETCH o.review r " +   //1
            "LEFT JOIN FETCH p.book b " +     //1
            "LEFT JOIN FETCH p.category c " +  //1
            "LEFT JOIN FETCH p.member m2 " +  //1
            "WHERE m.idx = :idx")
    List<Member> findMemberWithOrdersAndProducts(@Param("idx") Long idx);
}
