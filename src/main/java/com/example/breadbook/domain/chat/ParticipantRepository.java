package com.example.breadbook.domain.chat;

import com.example.breadbook.domain.chat.model.ChattingRoom;
import com.example.breadbook.domain.chat.model.Participant;
import com.example.breadbook.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    // 특정 유저가 속한 모든 채팅방 조회
    List<Participant> findByMember(Member member);
}
