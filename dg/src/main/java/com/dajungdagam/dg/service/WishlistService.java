package com.dajungdagam.dg.service;

import com.dajungdagam.dg.domain.dto.WishlistDto;
import com.dajungdagam.dg.domain.entity.TradePost;
import com.dajungdagam.dg.domain.entity.User;
import com.dajungdagam.dg.domain.entity.Wishlist;
import com.dajungdagam.dg.repository.TradePostRepository;
import com.dajungdagam.dg.repository.UserJpaRepository;
import com.dajungdagam.dg.repository.WishListJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class WishlistService {

    @Autowired
    private TradePostRepository tradePostRepository;
    @Autowired
    private UserJpaRepository userRepository;
    @Autowired
    private WishListJpaRepository wishlistRepository;

    public Boolean addPostToWishlist(WishlistDto wishlistDto){
        try {
            log.info("wishlist 1 : " + wishlistDto.getKakaoName());
            User user = userRepository.findByKakaoName(wishlistDto.getKakaoName());
            log.info("wishlist 2 : " + user.getKakaoName());
            Wishlist wishlist = wishlistRepository.findByUser(user);
            int postCategory = wishlistDto.getPostCategory();
            Long postId = wishlistDto.getPostId();

            if(wishlist == null)    throw new Exception("wishlist is null");

            // 0: 공동구매인 경우
            if (postCategory == 0) {
                // 공동구매 아직 구현 X

            } else {
                Optional<TradePost> tradePostObj = tradePostRepository.findById(postId);
                TradePost tradePost = tradePostObj.get();

                if(tradePost == null)    throw new Exception("wishlist is null");
                wishlist.getTradePost().add(tradePost);
                wishlistRepository.save(wishlist);
                return true;
            }
        } catch(Exception e){
            log.info(e.getMessage());
            return false;
        }
        return false;
    }

    public Wishlist getWishlistByKakaoName(String kakaoName) {
        User user = userRepository.findByKakaoName(kakaoName);
        Wishlist wishlist = wishlistRepository.findByUser(user);

        return wishlist;
    }
}
