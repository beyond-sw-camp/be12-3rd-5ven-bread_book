package com.example.breadbook.domain.member.repository;

import com.example.breadbook.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUseridAndProvider(String userid, String provider);
    Optional<Member> findByUsernameAndEmailAndProvider(String username, String email, String provider);
    Optional<Member> findByUseridAndEmailAndProvider(String userid, String email, String provider);

//    @Query("SELECT m FROM Member m " +
//            "LEFT JOIN FETCH m.products p " +
//            "LEFT JOIN FETCH p.book b " +
//            "LEFT JOIN FETCH p.orders o " +
//            "LEFT JOIN FETCH o.review r " +
//            "LEFT JOIN FETCH p.category c " +
//            "LEFT JOIN FETCH o.member m2 " +
//            "WHERE m.idx = :idx")
//    Page<Member> findMemberWithOrdersAndProducts(@Param("idx") Long idx, Pageable pageable);


    @Query("SELECT m FROM Member m " +
            "LEFT JOIN FETCH m.products p " +
            "LEFT JOIN FETCH p.book b " +
            "LEFT JOIN FETCH p.orders o " +
            "LEFT JOIN FETCH o.review r " +
            "LEFT JOIN FETCH p.category c " +
            "WHERE m.idx = :idx")
    List<Member> findMemberWithProductsAndOrders(Long idx);

    @Query("SELECT m FROM Member m " +
            "LEFT JOIN FETCH m.products p " +
            "LEFT JOIN FETCH p.member m2 " +
            "LEFT JOIN FETCH p.orders o " +
            "LEFT JOIN FETCH o.review r " +
            "WHERE m.idx = :idx")
    List<Member> findByMemberAndReview(Long idx);
}
