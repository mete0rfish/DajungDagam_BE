package com.dajungdagam.dg.service;

import com.dajungdagam.dg.domain.dto.TradePostDto;
import com.dajungdagam.dg.domain.dto.TradePostSummaryDto;
import com.dajungdagam.dg.domain.dto.UserResponseDto;
import com.dajungdagam.dg.domain.entity.TradePost;
import com.dajungdagam.dg.domain.entity.User;
import com.dajungdagam.dg.domain.entity.Wishlist;
import com.dajungdagam.dg.repository.TradePostRepository;
import com.dajungdagam.dg.repository.WishListJpaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TradePostService {

    @Autowired
    private TradePostRepository tradePostRepository;
    @Autowired
    private WishlistService wishlistService;
    @Autowired
    private WishListJpaRepository wishlistRepository;

    @Transactional
    public List<TradePostDto> searchPosts(String keyword) {
        List<TradePost> tradePosts = tradePostRepository.findByTitleContaining(keyword);
        List<TradePostDto> tradePostDtoList = new ArrayList<>();

        if (tradePosts.isEmpty()) return tradePostDtoList;

        for (TradePost tradePost : tradePosts) {
            tradePostDtoList.add(this.convertEntityToDto(tradePost));
        }

        return tradePostDtoList;
    }

    @Transactional
    public List<TradePostDto> searchPostsByUserId(int userId) {
        List<TradePost> tradePosts = tradePostRepository.findByUserId(userId);
        List<TradePostDto> tradePostDtoList = new ArrayList<>();

        if(tradePosts.isEmpty()){
            log.info("tradePosts is Empty");
            return tradePostDtoList;
        }

        for(TradePost tradePost : tradePosts){
            tradePostDtoList.add(this.convertEntityToDto(tradePost));
        }

        return tradePostDtoList;
    }

    @Transactional
    public List<TradePost> getAllTradePostWithUserId(int userId) {
        List<TradePost> tradePosts = tradePostRepository.findAllByUserId(userId);

        if(tradePosts.isEmpty()){
            log.info("tradePosts is Empty");
            return null;
        }

        return tradePosts;
    }

    private TradePostDto convertEntityToDto(TradePost tradePost) {
        return TradePostDto.builder()
                .id(tradePost.getId())
                .title(tradePost.getTitle())
                .content(tradePost.getContent())
                .user(tradePost.getUser())
                .createdTime(tradePost.getCreatedTime())
                .build();
    }

    @Transactional
    public Long savePost(TradePostDto tradePostDto) {
        return tradePostRepository.save(tradePostDto.toEntity()).getId();
    }

    @Transactional
    public List<TradePostDto> getPostlist() {
        List<TradePost> tradePosts = tradePostRepository.findAll();
        List<TradePostDto> tradePostDtoList = new ArrayList<>();

        for (TradePost tradePost : tradePosts) {
            TradePostDto tradePostDto = TradePostDto.builder()
                    .id(tradePost.getId())
                    .title(tradePost.getTitle())
                    .content(tradePost.getContent())
                    .user(tradePost.getUser())
                    .createdTime(tradePost.getCreatedTime())
                    .build();

            tradePostDtoList.add(tradePostDto);
        }

        return tradePostDtoList;
    }

    @Transactional
    public TradePostDto getPost(Long id) {
        Optional<TradePost> tradePostWrapper = tradePostRepository.findById(id);
        TradePost tradePost = tradePostWrapper.get();

        TradePostDto tradePostDto = TradePostDto.builder()
                .id(tradePost.getId())
                .title(tradePost.getTitle())
                .content(tradePost.getContent())
                .user(tradePost.getUser())
                .createdTime(tradePost.getCreatedTime())
                .build();

        return tradePostDto;
    }

    @Transactional
    public void deletePost(Long id) {
        tradePostRepository.deleteById(id);
    }


    public List<TradePostSummaryDto> getLikePosts() {
        List<TradePost> likePosts = tradePostRepository.findTop3ByOrderByWishlistDesc();
        List<TradePostSummaryDto> summaryDtos = new ArrayList<>();
        for (TradePost likePost : likePosts) {
            TradePostSummaryDto tradePostSummaryDto = TradePostSummaryDto.builder()
                    .id(likePost.getId())
                    .user(likePost.getUser())
                    .title(likePost.getTitle())
                    .tradeArea(likePost.getTradeArea())
                    .content(likePost.getContent())
                    .viewCount(likePost.getViewCount())
                    .wishlistCount(likePost.getWishlistCount())
                    .tradeStatus(likePost.getTradeStatus())
                    .build();
            summaryDtos.add(tradePostSummaryDto);
        }
        return summaryDtos;
    }

    @Transactional
    public boolean deleteAllPost(User user) {
        int userId = user.getId();
        String kakaoName = user.getKakaoName();

        List<TradePost> tradePostList= this.getAllTradePostWithUserId(userId);
        Wishlist wishlist = wishlistService.getWishlistByKakaoName(kakaoName);


        for(TradePost tradePost : wishlist.getTradePosts()) {
            tradePostRepository.deleteById(tradePost.getId());
        }

        for(TradePost tradePost : tradePostList) {
            tradePostRepository.deleteById(tradePost.getId());
        }

        wishlistRepository.deleteById(wishlist.getId());

        tradePostList= this.getAllTradePostWithUserId(userId);
        return tradePostList == null;
    }



}
