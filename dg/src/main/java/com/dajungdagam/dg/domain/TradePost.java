package com.dajungdagam.dg.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TradePost {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private int postId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "area_id")
    private Area area;              //지역

    @Column
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_type")
    private PostType postType;

    @Column(name = "trade_area")
    private String tradeArea;       //거래장소

    @Column
    private String content;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "view_count")
    private ViewCount viewCount;

    @Column(name = "wishlist_count")
    private WishListCount wishlistCount;

    @Column(name = "chat_link")
    private String chatLink;

    @Enumerated(EnumType.STRING)
    @Column(name = "trade_status")
    private TradeStatus tradeStatus;
}