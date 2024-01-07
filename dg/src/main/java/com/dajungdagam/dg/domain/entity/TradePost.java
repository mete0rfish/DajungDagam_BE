package com.dajungdagam.dg.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "trade_post")
public class TradePost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "item_id")
//    private Item item;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @Column(length = 50)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_type")
    private PostType postType;

    @Column(length = 10, name = "trade_area")
    private String tradeArea;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TIMESTAMP", name = "created_time")
    private LocalDateTime createdTime;

    @Column(columnDefinition = "TIMESTAMP", name = "update_time")
    private LocalDateTime updateTime;

    @Column(columnDefinition = "integer default 0", name = "view_count")
    private int viewCount;

    @Column(columnDefinition = "BIGINT", name = "wishlist_count")
    private Long wishlistCount;

    @Column(columnDefinition = "TEXT", name = "chat_link")
    private String chatLink;

    @Enumerated(EnumType.STRING)
    @Column(name = "trade_status")
    private TradeStatus tradeStatus;

    @OneToMany(mappedBy = "tradePost", cascade = CascadeType.ALL, //orphanRemoval = true,
                fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();


    @Builder
    public TradePost(Long id, User user, Area area, String title, PostType postType,
                     String tradeArea, String content, LocalDateTime createdTime,
                     LocalDateTime updateTime, int viewCount, Long wishlistCount,
                     String chatLink, TradeStatus tradeStatus, List<Image> images) {
        this.id = id;
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
        this.images = images;
    }

}