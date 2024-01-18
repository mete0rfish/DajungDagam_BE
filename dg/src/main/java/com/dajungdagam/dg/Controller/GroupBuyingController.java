package com.dajungdagam.dg.Controller;

import com.dajungdagam.dg.domain.dto.PostDto;
import com.dajungdagam.dg.domain.entity.Image;
import com.dajungdagam.dg.domain.entity.ItemCategory;
import com.dajungdagam.dg.service.ItemCategoryService;
import com.dajungdagam.dg.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class GroupBuyingController {
    private PostService postService;
    private ItemCategoryService itemCategoryService;

    public GroupBuyingController(PostService postService, ItemCategoryService itemCategoryService) {
        this.postService = postService;
        this.itemCategoryService = itemCategoryService;
    }

    @GetMapping("/group-buying/")
    public String list(Model model) {
        List<PostDto> postDtoList = postService.getPostlist();
        //Integer[] pageList = postService.getPageList();

        model.addAttribute("PostList", postDtoList);
        //model.addAttribute("pageList", pageList);
        return "list2";
    }

    @GetMapping("/group-buying/posts")
    public String saveForm(Model model) {
        List<ItemCategory> categories = itemCategoryService.getAllCategories();
        model.addAttribute("categories", categories);

        return "save2";
    }

//	@GetMapping("/trade/like-posts")
//	public String saveForm2() {
//		return "save";
//	}

    @PostMapping("/group-buying/posts")
    public String write(@ModelAttribute PostDto postDto,
                        @RequestParam MultipartFile[] images) throws IOException {

        // postService.savePost(postDto, images);

        return "redirect:/";
    }

    @GetMapping("/group-buying/posts/{id}")
    public String detail(@PathVariable Long id, Model model) {
        postService.updateView(id);

        PostDto postDto = postService.getPost(id);

        model.addAttribute("postDto", postDto);

        return "detail2";
    }

    @GetMapping("/group-buying/posts/update/{id}")
    public String edit(@PathVariable Long id, Model model) {
        PostDto postDto = postService.getPost(id);

        List<Image> images = postService.getImagesByTradePost(postDto.toEntity());

        model.addAttribute("postDto", postDto);
        model.addAttribute("images", images);

        return "update2";
    }

    @PatchMapping("/group-buying/posts/update/{id}")
    public String update(@PathVariable Long id, PostDto postDto,
                         HttpServletResponse response, @RequestParam MultipartFile[] images) throws IOException {
        postDto.setId(id);
        // PostService.updatePost(tradePostDto);
        postService.updatePost(postDto);

        response.setHeader(HttpHeaders.ALLOW, "GET, POST, PUT, PATCH, DELETE");

        return "redirect:/";
    }

    @DeleteMapping("group-buying/posts/{id}")
    public String delete(@PathVariable Long id) {
        postService.deletePost(id);

        return "redirect:/";
    }
    @GetMapping("/group-buying/search")
    public String search(@RequestParam String keyword, Model model) {
        List<PostDto> postDtoList = postService.searchPosts(keyword);
        model.addAttribute("PostList", postDtoList);

        return "list2";
    }


//    @GetMapping("/group-buying/posts/category/{categoryId}")
//    public String getPostsByCategory(@PathVariable Long categoryId, Model model) {
//        // PostService에서 카테고리 조회하는 메서드 사용
//        ItemCategory category = postService.getItemCategoryById(categoryId);
//
//        // 해당 카테고리에 속한 게시글들을 가져옴
//        List<PostDto> postsInCategory = postService.getTradePostsByCategory(category);
//
//        model.addAttribute("category", category);
//        model.addAttribute("posts", postsInCategory);
//
//        return "category2";
//    }

}
