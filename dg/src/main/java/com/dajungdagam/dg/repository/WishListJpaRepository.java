package com.dajungdagam.dg.repository;

import com.dajungdagam.dg.domain.entity.User;
import com.dajungdagam.dg.domain.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface WishListJpaRepository extends JpaRepository<Wishlist, Long> {
    Wishlist findByUser(User user);
}
