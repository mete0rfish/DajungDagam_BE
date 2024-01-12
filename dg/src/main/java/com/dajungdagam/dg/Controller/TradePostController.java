package com.dajungdagam.dg.Controller;



import com.dajungdagam.dg.domain.dto.*;
import com.dajungdagam.dg.domain.entity.*;
import com.dajungdagam.dg.service.UserService;

import com.dajungdagam.dg.service.ItemCategoryService;
import com.dajungdagam.dg.service.PostService;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class TradePostController {

    private PostService postService;
    private ItemCategoryService itemCategoryService;

    @Autowired
    private UserService userService;

    public TradePostController(PostService postService, ItemCategoryService itemCategoryService,
                               UserService userService) {
        this.postService = postService;
        this.itemCategoryService = itemCategoryService;
        this.userService = userService;
    }

    @GetMapping("/trade")
    public ResponseEntity<List<PostDto>> list() {
        List<PostDto> postDtoList = postService.getPostlist();

        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/trade/posts")
    public ResponseEntity<PostDto> saveForm() {

        PostDto postDto = new PostDto();

        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping("/trade/like-posts")
    public List<TradePostSummaryDto> liked_list() {

        List<TradePostSummaryDto> likePostsSummaryDtos = postService.getLikePosts();
        return likePostsSummaryDtos;
        //인기글 목록에는 모든 정보가 필요하지 않다. -> TradePostSummaryDto를 이용하여 반환
        //return new TradePostSummaryDto();
    }

    @PostMapping(value = "/trade/posts")
    public ResponseEntity<String> write(PostDto postDto, Authentication authentication,
                                        @RequestPart MultipartFile[] images) throws IOException {
        log.info("title: " + postDto.getTitle() + " , content : " + postDto.getContent());
        try {
            if(authentication == null)
                throw new Exception("authentication is null. non user Info");

            String kakaoName = authentication.getName();
            UserResponseDto userResponseDto = userService.findByUserKakaoNickName(kakaoName);
            postDto.setUser(userResponseDto.getUser());

        }catch(Exception e){
            e.getStackTrace();
        } finally {
            postService.savePost(postDto, images);
        }

        return ResponseEntity.ok().body("게시글 생성 완료");

    }


    @GetMapping("/trade/posts/{post_id}")
    public ResponseEntity<PostDto> detail(@PathVariable("post_id") Long post_id) {
        postService.updateView(post_id);
        PostDto postDto = postService.getPost(post_id);

        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

//    @GetMapping("/trade/posts/update/{post_id}")
//    public ResponseEntity<Map<String, Object>> edit(@PathVariable("post_id") Long post_id) {
//        PostDto postDto = postService.getPost(post_id);
//
//        List<Image> images = postService.getImagesByTradePost(postDto.toEntity());
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("postDto", postDto);
//        response.put("images", images);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @GetMapping("/trade/posts/update/{post_id}")
    public ResponseEntity<TradePostSimpleDto> edit(@PathVariable("post_id") Long post_id) {
        PostDto postDto = postService.getPost(post_id);

        ModelMapper modelMapper = new ModelMapper();
        TradePostSimpleDto tradePostSimpleDto = modelMapper.map(postDto, TradePostSimpleDto.class);

        return ResponseEntity.ok(tradePostSimpleDto);
    }

    @PatchMapping("/trade/posts/update/{post_id}")
    public ResponseEntity<?> update(@PathVariable("post_id") Long post_id, PostDto postDto,
                                    @RequestPart MultipartFile[] images) {
        postDto.setId(post_id);
        postService.updatePost(postDto);

        return ResponseEntity.ok().body("게시글 수정 완료");
    }


    @DeleteMapping("trade/posts/{post_id}")
    public ResponseEntity<?> delete(@PathVariable("post_id") Long post_id) {
        postService.deletePost(post_id);

        return ResponseEntity.ok().body("게시글 삭제 완료");
    }

    @GetMapping("/trade/posts/search")
    public ResponseEntity<List<PostDto>> search(@RequestParam String keyword) {
        List<PostDto> postDtoList = postService.searchPosts(keyword);

        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }
    

    @GetMapping("/trade/posts/category/{itemCategory}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable ItemCategory itemCategory) {
        List<PostDto> postsByCategory = postService.getPostsByCategory(itemCategory);
        return new ResponseEntity<>(postsByCategory, HttpStatus.OK);
    }


    @GetMapping("/trade/posts/status/{tradeStatus}")
    public ResponseEntity<List<PostDto>> getPostsByStatus(@PathVariable TradeStatus tradeStatus) {
        List<PostDto> postsByStatus = postService.getPostsByStatus(tradeStatus);
        return new ResponseEntity<>(postsByStatus, HttpStatus.OK);
    }

    @GetMapping("/trade/posts/area/{area}")
    public ResponseEntity<List<PostDto>> getPostsByArea(@PathVariable Area area) {
        List<PostDto> postsByArea = postService.getPostsByArea(area);
        return new ResponseEntity<>(postsByArea, HttpStatus.OK);
    }



}