package com.dajungdagam.dg.repository;

import com.dajungdagam.dg.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Integer> {

    User findByNickName(String nickName);
    User findByKakaoName(String kakaoName);
}
