package com.example.breadbook.domain.chat.model;

import com.example.breadbook.domain.product.model.Product;
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

    @ManyToOne
    @JoinColumn(name = "product_idx")
    private Product product;

    @Column(columnDefinition = "TEXT")
    private String lastChat;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "room")
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<Participant> participants = new ArrayList<>();
}

