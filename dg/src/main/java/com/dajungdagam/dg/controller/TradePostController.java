package com.dajungdagam.dg.Controller;


import com.dajungdagam.dg.domain.dto.TradePostDto;
import com.dajungdagam.dg.domain.entity.Image;
import com.dajungdagam.dg.domain.entity.ItemCategory;
import com.dajungdagam.dg.domain.entity.TradePost;
import com.dajungdagam.dg.service.ItemCategoryService;
import com.dajungdagam.dg.service.TradePostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TradePostController {

    private TradePostService tradePostService;
    private ItemCategoryService itemCategoryService;

    public TradePostController(TradePostService tradePostService, ItemCategoryService itemCategoryService) {
        this.tradePostService = tradePostService;
        this.itemCategoryService = itemCategoryService;
    }

    @GetMapping("/trade/")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNum) {
        List<TradePostDto> tradePostDtoList = tradePostService.getPostlist(pageNum);
        Integer[] pageList = tradePostService.getPageList(pageNum);

        model.addAttribute("TradePostList", tradePostDtoList);
        model.addAttribute("pageList", pageList);
        return "list";
    }

    @GetMapping("/trade/posts")
    public String saveForm(Model model) {
        List<ItemCategory> categories = itemCategoryService.getAllCategories();
        model.addAttribute("categories", categories);

        return "save";
    }

//	@GetMapping("/trade/like-posts")
//	public String saveForm2() {
//		return "save";
//	}

    @PostMapping("/trade/posts")
    public String write(@ModelAttribute TradePostDto tradePostDto,
                        @RequestParam MultipartFile[] images) throws IOException {

        tradePostService.savePost(tradePostDto, images);

        return "redirect:/";
    }

    @GetMapping("/trade/posts/{id}")
    public String detail(@PathVariable Long id, Model model) {
        tradePostService.updateView(id);

        TradePostDto tradePostDto = tradePostService.getPost(id);

        model.addAttribute("tradepostDto", tradePostDto);

        return "detail";
    }

    @GetMapping("/trade/posts/update/{id}")
    public String edit(@PathVariable Long id, Model model) {
        TradePostDto tradePostDto = tradePostService.getPost(id);

        List<Image> images = tradePostService.getImagesByTradePost(tradePostDto.toEntity());

        model.addAttribute("tradepostDto", tradePostDto);
        model.addAttribute("images", images);

        return "update";
    }

    @PatchMapping("/trade/posts/update/{id}")
    public String update(@PathVariable Long id, TradePostDto tradePostDto,
                         HttpServletResponse response, @RequestParam MultipartFile[] images) throws IOException {
        tradePostDto.setId(id);
        //tradePostService.updatePost(tradePostDto);
        tradePostService.updatePost(tradePostDto);

        response.setHeader(HttpHeaders.ALLOW, "GET, POST, PUT, PATCH, DELETE");

        return "redirect:/";
    }

    @DeleteMapping("trade/posts/{id}")
    public String delete(@PathVariable Long id) {
        tradePostService.deletePost(id);

        return "redirect:/";
    }

//    @DeleteMapping("trade/posts/deleteImage/{id}/{status.index}")
//    public String deleteimage(@PathVariable Long id, TradePostDto tradePostDto,
//                              @RequestParam MultipartFile[] images) {
//        tradePostService.deleteImage(id);
//
//        return "redirect:/";
//    }

    @GetMapping("/trade/search")
    public String search(@RequestParam String keyword, Model model) {
        List<TradePostDto> tradePostDtoList = tradePostService.searchPosts(keyword);
        model.addAttribute("TradePostList", tradePostDtoList);

        return "list";
    }

    @GetMapping("/trade/posts/category/{categoryId}")
    public String getPostsByCategory(@PathVariable Long categoryId, Model model) {
        // TradePostService에서 카테고리 조회하는 메서드 사용
        ItemCategory category = tradePostService.getItemCategoryById(categoryId);

        // 해당 카테고리에 속한 게시글들을 가져옴
        List<TradePost> postsInCategory = tradePostService.getTradePostsByCategory(category);

        model.addAttribute("category", category);
        model.addAttribute("posts", postsInCategory);

        return "category";
    }

}