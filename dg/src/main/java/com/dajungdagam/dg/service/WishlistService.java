package com.dajungdagam.dg.service;

import com.dajungdagam.dg.domain.dto.WishlistDto;
import com.dajungdagam.dg.domain.entity.TradePost;
import com.dajungdagam.dg.domain.entity.User;
import com.dajungdagam.dg.domain.entity.Wishlist;
import com.dajungdagam.dg.repository.TradePostRepository;
import com.dajungdagam.dg.repository.UserJpaRepository;
import com.dajungdagam.dg.repository.WishListJpaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WishlistService {

    @Autowired
    private TradePostRepository tradePostRepository;
    @Autowired
    private UserJpaRepository userRepository;
    @Autowired
    private WishListJpaRepository wishlistRepository;

    public Wishlist addWishlist(String kakaoName){
        User user = userRepository.findByKakaoName(kakaoName);

        Wishlist wishlist = new Wishlist(user);
        wishlistRepository.save(wishlist);

        log.info("찜목록 생성됨");

        return wishlist;
    }

    // TODO: 이미 찜목록되어 있으면, 알려야하나?
    @Transactional
    public Wishlist addPostToWishlist(WishlistDto wishlistDto){
        Wishlist wishlist = null;
        try {
            //log.info("wishlist 1 : " + wishlistDto.getKakaoName());
            User user = userRepository.findByKakaoName(wishlistDto.getKakaoName());
            //log.info("wishlist 2 : " + user.getKakaoName());
            wishlist = wishlistRepository.findByUser(user);
            //log.info("wish: " + wishlist.toString());

            int postCategory = wishlistDto.getPostCategory();
            Long postId = wishlistDto.getPostId();

            if(wishlist == null)    throw new Exception("wishlist is null");

            // 0: 공동구매인 경우
            if (postCategory == 0) {
                // 공동구매 아직 구현 X

            } else {
                Optional<TradePost> tradePostObj = tradePostRepository.findById(postId);
                TradePost tradePost = tradePostObj.get();
                log.info("TradePost: "+ tradePost.toString());

                if(tradePost == null)    throw new Exception("wishlist is null");

                wishlist.addTradePost(tradePost);
                wishlistRepository.save(wishlist);

                log.info("wishlist의 tradeposts: " + wishlist.getTradePosts().toString());

            }
        } catch(Exception e){
            log.info(e.getMessage());
            return null;
        }
        return wishlist;
    }


    public Wishlist deletePostAtWishlist(String kakaoName, int postCategory, Long postId) {
        Wishlist wishlist= null;
        try {
            User user = userRepository.findByKakaoName(kakaoName);
            wishlist = wishlistRepository.findByUser(user);

            if(wishlist == null)    throw new Exception("wishlist is null");

            // 0: 공동구매인 경우
            if (postCategory == 0) {
                // 공동구매 아직 구현 X

            } else {
                Optional<TradePost> tradePostObj = tradePostRepository.findById(postId);
                TradePost tradePost = tradePostObj.get();

                if(tradePost == null)    throw new Exception("wishlist is null");
                List<TradePost> tradePosts = wishlist.getTradePosts();
                tradePosts.stream().filter(post  -> {
                    if(Objects.equals(post.getId(), postId)) return true;
                    else return false;
                })
                        .toList().forEach(tradePosts::remove);

                wishlistRepository.save(wishlist);

                log.info("위시 리스트에서 " + postId + "번 게시글이 삭제됨.");
            }
        } catch(Exception e) {
            log.info(e.getMessage());
        }
        return wishlist;
    }

    public Wishlist getWishlistByKakaoName(String kakaoName) {
        User user = userRepository.findByKakaoName(kakaoName);

        return wishlistRepository.findByUser(user);
    }
}
