package com.example.breadbook.domain.chat.model;

import com.example.breadbook.domain.member.model.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "room_idx")
    private ChattingRoom room;

    @ManyToOne
    @JoinColumn(name = "member_idx")
    private Member member;

    public Participant(ChattingRoom room, Member member) {
        this.room = room;
        this.member = member;
    }
}
