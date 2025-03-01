package com.example.breadbook.domain.chat;

import com.example.breadbook.domain.chat.model.ChattingRoom;
import com.example.breadbook.domain.chat.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    //  특정 채팅방에 있는 모든 참여자 조회 (판매자 & 구매자)
    List<Participant> findByRoom(ChattingRoom room);

    //  특정 유저가 특정 채팅방에 존재하는지 확인 (중복 방지)
    boolean existsByRoomAndUserIdx(ChattingRoom room, Long userIdx);
}
