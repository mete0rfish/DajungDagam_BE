package com.dajungdagam.dg.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table
@Getter
public class Wishlist {
    @Id
    @GeneratedValue
    @Column(name = "wishlist_id")
    private Long id;

    @Column(unique = true, name = "wish_id")
    private Long wishId;

    @OneToMany(mappedBy = "wishlist")
    private ArrayList<TradePost> tradePost;

    @Column
    private LocalDateTime createdTime;

    @OneToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;


    @Builder
    public Wishlist(Long id, ArrayList<TradePost> tradePost, LocalDateTime createdTime, User user) {
        this.id = id;
        this.tradePost = tradePost;
        this.createdTime = createdTime;
        this.user = user;
    }

    public Wishlist() {

    }
}
