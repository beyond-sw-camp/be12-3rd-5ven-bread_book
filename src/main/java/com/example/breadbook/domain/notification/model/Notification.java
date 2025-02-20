package com.example.breadbook.domain.notification.model;

import com.example.breadbook.domain.member.model.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private Long member_Idx;
    private String message;
    private Boolean is_read;
    private Date created_at;

    @ManyToOne
    @JoinColumn(name = "member_idx")
    private Member member;

}
