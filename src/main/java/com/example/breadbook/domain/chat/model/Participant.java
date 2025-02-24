package com.example.breadbook.domain.chat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "participant")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "room_idx")
    private ChattingRoom room;

    @Column(name = "user_idx")
    private Long userIdx;

    public Participant(ChattingRoom room, Long userIdx) {
        this.room = room;
        this.userIdx = userIdx;
    }
}
