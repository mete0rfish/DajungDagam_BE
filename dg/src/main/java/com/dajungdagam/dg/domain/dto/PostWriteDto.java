package com.dajungdagam.dg.domain.dto;

import com.dajungdagam.dg.domain.entity.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter // Setter랑 ToString 없으면 DB 내에 저장이 안되더라.
@ToString
@NoArgsConstructor
public class PostWriteDto {

    public Long id;

    public String guName;
    public String dongName;
    public String title;
    public int postType;
    public String content;
    public LocalDateTime createdTime;
    public LocalDateTime updatedTime;
    public int viewCount;
    // price
    public Integer wishlistCount;
    public String chatLink;
    public LocalDateTime deadline;
    public String itemCategory;



    @Builder

    public PostWriteDto(Long id, String guName, String dongName, String title, int postType, String content, LocalDateTime createdTime, LocalDateTime updatedTime, int viewCount, Integer wishlistCount, String chatLink, LocalDateTime deadline, String itemCategory) {
        this.id = id;
        this.guName = guName;
        this.dongName = dongName;
        this.title = title;
        this.postType = postType;
        this.content = content;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.viewCount = viewCount;
        this.wishlistCount = wishlistCount;
        this.chatLink = chatLink;
        this.deadline = deadline;
        this.itemCategory = itemCategory;
    }
}
