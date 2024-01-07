package com.dajungdagam.dg.domain.dto;


import com.dajungdagam.dg.domain.entity.*;
import lombok.*;

import java.time.*;
@Getter
@Setter // Setter랑 ToString 없으면 DB 내에 저장이 안되더라.
@ToString
@NoArgsConstructor
public class TradePostDto {

    private Long id;
    private User user;
    private Area area;
    private String title;
    private PostType postType;
    private String tradeArea;
    private String content;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
    private Long viewCount;
    private Long wishlistCount;
    private String chatLink;
    private TradeStatus tradeStatus;
    private Wishlist wishlist;

    public TradePost toEntity() {
        return TradePost.builder()
                .id(id)
                .user(user)
                .area(area)
                .title(title)
                .postType(postType)
                .tradeArea(tradeArea)
                .content(content)
                .createdTime(createdTime)
                .updateTime(updateTime)
                .viewCount(viewCount)
                .wishlistCount(wishlistCount)
                .chatLink(chatLink)
                .tradeStatus(tradeStatus)
                .build();
    }

    @Builder
    public TradePostDto(Long id, User user, Area area, String title, PostType postType,
                        String tradeArea, String content, LocalDateTime createdTime,
                        LocalDateTime updateTime, Long viewCount, Long wishlistCount,
                        String chatLink, TradeStatus tradeStatus, Wishlist wishlist)
    {
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
        this.wishlist = wishlist;
    }

}
