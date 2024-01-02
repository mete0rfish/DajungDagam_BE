package com.dajungdagam.dg.repository;

import com.dajungdagam.dg.domain.entity.TradePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradePostRepository extends JpaRepository<TradePost, Long> {
    List<TradePost> findByTitleContaining(String keyword);

    @Modifying
    @Query("update TradePost p set p.viewCount = p.viewCount + 1 where p.id = :id")
    int updateviewCount(Long id);
}
