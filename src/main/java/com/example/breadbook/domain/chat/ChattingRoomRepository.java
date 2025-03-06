package com.example.breadbook.domain.chat;

import com.example.breadbook.domain.chat.model.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, Long> {

    @Query("SELECT r FROM ChattingRoom r " +
            "LEFT JOIN FETCH r.product p " +
            "LEFT JOIN FETCH r.participants c " +
            "LEFT JOIN FETCH c.member m " +
            "WHERE r.idx = :idx")
    List<ChattingRoom> findByMemberAndProduct(Long idx);
}
