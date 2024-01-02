package com.dajungdagam.dg.service;

import com.dajungdagam.dg.domain.dto.TradePostDto;
import com.dajungdagam.dg.domain.entity.TradePost;
import com.dajungdagam.dg.repository.TradePostRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TradePostService {

    private TradePostRepository tradePostRepository;

    private static final int BLOCK_PAGE_NUM_COUNT = 5; // 블럭에 존재하는 페이지 수
    private static final int PAGE_POST_COUNT = 9; // 한 페이지에 존재하는 게시글 수

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
    public List<TradePostDto> getPostlist(Integer pageNum) {

         Page<TradePost> page = tradePostRepository
                 .findAll(PageRequest.of(pageNum-1, PAGE_POST_COUNT,
                         Sort.by(Sort.Direction.ASC, "createdTime")));

         //List<TradePost> tradePosts = tradePostRepository.findAll();
        List<TradePost> tradePosts = page.getContent();
        List<TradePostDto> tradePostDtoList = new ArrayList<>();

        for (TradePost tradePost : tradePosts) {
            TradePostDto tradePostDto = TradePostDto.builder()
                    .id(tradePost.getId())
                    .title(tradePost.getTitle())
                    .content(tradePost.getContent())
                    .user(tradePost.getUser())
                    .createdTime(tradePost.getCreatedTime())
                    .viewCount(tradePost.getViewCount())
                    .build();
            tradePostDtoList.add(tradePostDto);
        }

        return tradePostDtoList;
    }

    @Transactional
    public Integer[] getPageList(Integer curPageNum) {
         Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // 총 게시글 수
         Double postsTotalCount = Double.valueOf(this.getPostCount());

         // 총 게시글 수를 기준으로 계산한 마지막 페이지 번호 계산
         Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
         Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                 ? curPageNum + BLOCK_PAGE_NUM_COUNT
                 : totalLastPageNum;

         // 페이지 시작 번호 조정
         curPageNum = (curPageNum<=3) ? 1 : curPageNum-2;

         // 페이지 번호 할당
         for(int val=curPageNum, i=0; val<=blockLastPageNum; val++, i++) {
             pageList[i] = val;
         }

         return pageList;
    }

    @Transactional
    public Long getPostCount() {
         return tradePostRepository.count();
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
                .viewCount(tradePost.getViewCount())
                .build();

        return tradePostDto;
    }

    @Transactional
    public void deletePost(Long id) {
        tradePostRepository.deleteById(id);
    }


    @Transactional // 조회수 기능
    public int updateView(Long id) {
        return tradePostRepository.updateviewCount(id);
    }
}
