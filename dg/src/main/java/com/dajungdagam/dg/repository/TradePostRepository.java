package com.dajungdagam.dg.repository;

import com.dajungdagam.dg.domain.entity.TradePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradePostRepository extends JpaRepository<TradePost, Long> {
    List<TradePost> findByTitleContaining(String keyword);
}
