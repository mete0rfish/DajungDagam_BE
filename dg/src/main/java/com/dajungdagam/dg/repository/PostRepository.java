package com.dajungdagam.dg.repository;

import com.dajungdagam.dg.domain.dto.PostDto;
import com.dajungdagam.dg.domain.entity.ItemCategory;
import com.dajungdagam.dg.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitleContaining(String keyword);

    @Modifying // 조회수 구현
    @Query("update Post p set p.viewCount = p.viewCount + 1 where p.id = :id")
    int updateviewCount(Long id);

    // 해당 카테고리에 속한 게시글 검색
    List<PostDto> findByItemCategory(ItemCategory itemCategory);

//    // 특정 타입의 글 조회
//    List<PostDto> findByType(int postType);

}
