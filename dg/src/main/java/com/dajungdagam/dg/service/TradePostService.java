package com.dajungdagam.dg.service;

import com.dajungdagam.dg.domain.dto.ImageDto;
import com.dajungdagam.dg.domain.dto.TradePostDto;
import com.dajungdagam.dg.domain.entity.Image;
import com.dajungdagam.dg.domain.entity.TradePost;
import com.dajungdagam.dg.repository.ImageRepository;
import com.dajungdagam.dg.repository.TradePostRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TradePostService {

    private TradePostRepository tradePostRepository;
    private ImageRepository imageRepository;

    private static final int BLOCK_PAGE_NUM_COUNT = 5; // 블럭에 존재하는 페이지 수
    private static final int PAGE_POST_COUNT = 9; // 한 페이지에 존재하는 게시글 수

     public TradePostService(TradePostRepository tradePostRepository, ImageRepository imageRepository) {
        this.tradePostRepository = tradePostRepository;
        this.imageRepository = imageRepository;
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
                .user(tradePost.getUser())
                .title(tradePost.getTitle())
                .postType(tradePost.getPostType())
                .tradeArea(tradePost.getTradeArea())
                .content(tradePost.getContent())
                .createdTime(tradePost.getCreatedTime())
                .updateTime(tradePost.getUpdateTime())
                .viewCount(tradePost.getViewCount())
                .wishlistCount(tradePost.getWishlistCount())
                .chatLink(tradePost.getChatLink())
                .tradeStatus(tradePost.getTradeStatus())
                .build();
    }

    // 절대 경로임. Mac 기준 경로임을 유의
    private final String imagePath = "/Users/choehyeontae/Desktop/images/";

    @Transactional // 게시글 작성 이미지 업로드 기능 추가
    public void savePost(TradePostDto tradePostDto, MultipartFile[] images) throws IOException {

         Path uploadPath = Paths.get(imagePath);

         // 만약 경로가 없다면 경로 생성
         if (!Files.exists(uploadPath)) {
             try {
                 Files.createDirectories(uploadPath);
             } catch (IOException e) {
                 // 예외 처리 추가 (예: 로깅)
                 e.printStackTrace();
             }
         }

         // 게시글 DB에 저장 후 pk을 받아옴
         Long id = tradePostRepository.save(tradePostDto.toEntity()).getId();
         TradePost tradePost = tradePostRepository.findById(id).get();
         if (images != null && images.length > 0) {

             // 최소 하나의 이미지를 업로드하도록 검증
             if (images.length < 1) {
                 throw new IllegalArgumentException("최소 하나의 이미지를 업로드해야 합니다.");
             }

             // 최대 5개의 이미지만 업로드하도록 검증
             if (images.length > 5) {
                 throw new IllegalArgumentException("최대 5개의 이미지만 업로드할 수 있습니다.");
             }

             // 파일 정보 저장
             for(MultipartFile image : images) {
                 // 파일명 추출
                 String originalImageName = image.getOriginalFilename();

                 // 이미지가 비어있는지 확인
                 if (image.isEmpty()) {
                     throw new IllegalArgumentException("이미지가 비어있습니다.");
                 }

                 if (originalImageName != null && !originalImageName.isEmpty()) {
                     // 확장자 추출 및 소문자로 변환
                     String formatType = originalImageName.substring(
                             originalImageName.lastIndexOf(".")).toLowerCase();

                     // jpg, jpeg 또는 png 형식의 이미지만 업로드 가능하도록 검증
                     if (!formatType.equals(".jpg") && !formatType.equals(".jpeg") && !formatType.equals(".png")) {
                         throw new IllegalArgumentException("jpg, jpeg 또는 png 형식의 이미지만 업로드 가능합니다.");
                     }

                     // UUID 생성
                     String uuid = UUID.randomUUID().toString();

                     // 경로 지정
                     String path = imagePath + uuid + originalImageName;

                     // 경로에 파일을 저장 (DB 아님)
                     image.transferTo(new File(path) );

                     Image image1 = Image.builder()
                             .imagePath(imagePath)
                             .imageName(originalImageName)
                             .uuid(uuid)
                             .imageType(formatType)
                             .imageSize(image.getSize())
                             .tradePost(tradePost)
                             .build();

                     imageRepository.save(image1);

                 }
             }
         }

     }


    @Transactional
    public List<TradePostDto> getPostlist(Integer pageNum) {

         Page<TradePost> page = tradePostRepository
                 .findAll(PageRequest.of(pageNum-1, PAGE_POST_COUNT,
                         Sort.by(Sort.Direction.ASC, "createdTime")));

        List<TradePost> tradePosts = page.getContent();
        List<TradePostDto> tradePostDtoList = new ArrayList<>();

        for (TradePost tradePost : tradePosts) {
            TradePostDto tradePostDto = TradePostDto.builder()
                    .id(tradePost.getId())
                    .user(tradePost.getUser())
                    .title(tradePost.getTitle())
                    .postType(tradePost.getPostType())
                    .tradeArea(tradePost.getTradeArea())
                    .content(tradePost.getContent())
                    .createdTime(tradePost.getCreatedTime())
                    .updateTime(tradePost.getUpdateTime())
                    .viewCount(tradePost.getViewCount())
                    .wishlistCount(tradePost.getWishlistCount())
                    .chatLink(tradePost.getChatLink())
                    .tradeStatus(tradePost.getTradeStatus())
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
                .user(tradePost.getUser())
                .title(tradePost.getTitle())
                .postType(tradePost.getPostType())
                .tradeArea(tradePost.getTradeArea())
                .content(tradePost.getContent())
                .createdTime(tradePost.getCreatedTime())
                .updateTime(tradePost.getUpdateTime())
                .viewCount(tradePost.getViewCount())
                .wishlistCount(tradePost.getWishlistCount())
                .chatLink(tradePost.getChatLink())
                .tradeStatus(tradePost.getTradeStatus())
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
