package com.dajungdagam.dg.repository;

import com.dajungdagam.dg.domain.entity.User;
import com.dajungdagam.dg.domain.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListJpaRepository extends JpaRepository<Wishlist, Integer> {
    Wishlist findByUser(User user);

}
