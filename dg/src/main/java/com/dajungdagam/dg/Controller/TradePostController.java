package com.dajungdagam.dg.controller;



import com.dajungdagam.dg.domain.dto.PostDto;
import com.dajungdagam.dg.domain.dto.TradePostSummaryDto;
import com.dajungdagam.dg.domain.dto.UserResponseDto;
import com.dajungdagam.dg.service.UserService;

import com.dajungdagam.dg.domain.entity.Image;
import com.dajungdagam.dg.domain.entity.ItemCategory;

import com.dajungdagam.dg.service.ItemCategoryService;
import com.dajungdagam.dg.service.PostService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
public class TradePostController {

    private PostService postService;
    private ItemCategoryService itemCategoryService;

    @Autowired
    private UserService userService;



    public TradePostController(PostService postService, ItemCategoryService itemCategoryService) {
        this.postService = postService;
        this.itemCategoryService = itemCategoryService;
    }

    @GetMapping("/trade/")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNum) {
        List<PostDto> postDtoList = postService.getPostlist(pageNum);
        Integer[] pageList = postService.getPageList(pageNum);

        model.addAttribute("PostList", postDtoList);
        model.addAttribute("pageList", pageList);
        return "list1";
    }

    @GetMapping("/trade/posts")
    public String saveForm(Model model) {
        List<ItemCategory> categories = itemCategoryService.getAllCategories();
        model.addAttribute("categories", categories);

        return "save1";
    }

    @GetMapping("/trade/like-posts")
    public List<TradePostSummaryDto> liked_list() {

        List<TradePostSummaryDto> likePostsSummaryDtos = postService.getLikePosts();
        return likePostsSummaryDtos;
        //인기글 목록에는 모든 정보가 필요하지 않다. -> TradePostSummaryDto를 이용하여 반환
        //return new TradePostSummaryDto();
    }

    @PostMapping(value = "/trade/posts",  consumes = MediaType.APPLICATION_JSON_VALUE)
    public String write(@RequestBody PostDto postDto, Authentication authentication, @RequestParam MultipartFile[] images) throws IOException {
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

        return "redirect:/";
    }

    @GetMapping("/trade/posts/{id}")
    public String detail(@PathVariable Long id, Model model) {
        postService.updateView(id);

        PostDto postDto = postService.getPost(id);

        model.addAttribute("postDto", postDto);

        return "detail1";
    }

    @GetMapping("/trade/posts/update/{id}")
    public String edit(@PathVariable Long id, Model model) {
        PostDto postDto = postService.getPost(id);

        List<Image> images = postService.getImagesByTradePost(postDto.toEntity());

        model.addAttribute("postDto", postDto);
        model.addAttribute("images", images);

        return "update1";
    }

    @PatchMapping("/trade/posts/update/{id}")
    public String update(@PathVariable Long id, PostDto postDto,
                         HttpServletResponse response, @RequestParam MultipartFile[] images) throws IOException {
        postDto.setId(id);
        //tradePostService.updatePost(tradePostDto);
        postService.updatePost(postDto);

        response.setHeader(HttpHeaders.ALLOW, "GET, POST, PUT, PATCH, DELETE");

        return "redirect:/";
    }

    @DeleteMapping("trade/posts/{id}")
    public String delete(@PathVariable Long id) {
        postService.deletePost(id);

        return "redirect:/";
    }
    @GetMapping("/trade/search")
    public String search(@RequestParam String keyword, Model model) {
        List<PostDto> postDtoList = postService.searchPosts(keyword);
        model.addAttribute("TradePostList", postDtoList);

        return "list1";
    }


    @GetMapping("/trade/posts/category/{categoryId}")
    public String getPostsByCategory(@PathVariable Long categoryId, Model model) {
        // TradePostService에서 카테고리 조회하는 메서드 사용
        ItemCategory category = postService.getItemCategoryById(categoryId);

        // 해당 카테고리에 속한 게시글들을 가져옴
        List<PostDto> postsInCategory = postService.getTradePostsByCategory(category);

        model.addAttribute("category", category);
        model.addAttribute("posts", postsInCategory);

        return "category1";
    }



}