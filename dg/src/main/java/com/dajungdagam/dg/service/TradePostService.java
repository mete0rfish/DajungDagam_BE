package com.dajungdagam.dg.service;

import com.dajungdagam.dg.domain.dto.TradePostDto;
import com.dajungdagam.dg.domain.dto.UserResponseDto;
import com.dajungdagam.dg.domain.entity.TradePost;
import com.dajungdagam.dg.domain.entity.User;
import com.dajungdagam.dg.repository.TradePostRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TradePostService {

    private TradePostRepository tradePostRepository;

    public TradePostService(TradePostRepository tradePostRepository) {
        this.tradePostRepository = tradePostRepository;
    }

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



}
