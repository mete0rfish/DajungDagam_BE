package com.dajungdagam.dg.domain.dto;


import com.dajungdagam.dg.domain.entity.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.*;

@Getter
@Setter // Setter랑 ToString 없으면 DB 내에 저장이 안되더라.
@ToString
@NoArgsConstructor
public class PostDto {

    public Long id;

    @JsonIgnore
    public User user;
    public Area area;
    public String title;
    public int postType;
    public String tradeArea;
    public String content;
    public LocalDateTime createdTime;
    public LocalDateTime updateTime;
    public int viewCount;
    public Long wishlistCount;
    public String chatLink;
    public TradeStatus tradeStatus;

    public Wishlist wishlist;


    public ItemCategory itemCategory;


    public Post toEntity() {
        return Post.builder()
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
                .itemCategory(itemCategory)
                .build();
    }

    @Builder
    public PostDto(Long id, User user, Area area, String title, int postType,
                   String tradeArea, String content, LocalDateTime createdTime,
                   LocalDateTime updateTime, int viewCount, Long wishlistCount,
                   String chatLink, TradeStatus tradeStatus, ItemCategory itemCategory)

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
        this.itemCategory = itemCategory;

    }

}
