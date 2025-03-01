package com.example.breadbook.domain.chat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChattingRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, unique = true)
    private String identifier;

    @Column(name = "product_idx")
    private Long productIdx;

    @Column(name = "book_title")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String lastChat;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "room")
    private List<Message> messages = new ArrayList<>();
}
