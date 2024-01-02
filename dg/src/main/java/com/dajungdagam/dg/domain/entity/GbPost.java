package com.dajungdagam.dg.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Getter
public class GbPost {
    @Id
    @GeneratedValue
    @Column(name = "gb_id")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area;              //지역

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_type")
    private PostType postType;

    @Column(name = "trade_area")
    private String tradeArea;       //거래장소

    @Column(name = "content")
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

    @Column(name = "person_count")
    private int personCount;            //모집인원

    @Column(name = "person_current_count")
    private int personCurrentCount;     //현재 모집된 인원

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Builder
    public GbPost(Long postId, User user, Area area, String title, PostType postType, String tradeArea, String content, LocalDateTime createdTime, LocalDateTime updateTime, ViewCount viewCount, WishListCount wishlistCount, String chatLink, TradeStatus tradeStatus, int personCount, int personCurrentCount, LocalDateTime deadline) {
        this.postId = postId;
        this.user = user;
        this.area = area;
        this.title = title;
        this.postType = postType;
        this.tradeArea = tradeArea;
        this.content = content;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
        this.viewCount = viewCount;
        this.wishlistCount = wishlistCount;
        this.chatLink = chatLink;
        this.tradeStatus = tradeStatus;
        this.personCount = personCount;
        this.personCurrentCount = personCurrentCount;
        this.deadline = deadline;
    }

}