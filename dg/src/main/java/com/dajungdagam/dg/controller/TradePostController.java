package com.dajungdagam.dg.controller;


import com.dajungdagam.dg.domain.dto.TradePostDto;
import com.dajungdagam.dg.domain.dto.UserResponseDto;
import com.dajungdagam.dg.service.TradePostService;
import com.dajungdagam.dg.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TradePostController {

    private TradePostService tradePostService;

    @Autowired
    private UserService userService;

    public TradePostController(TradePostService tradePostService) {
        this.tradePostService = tradePostService;
    }

    @GetMapping("/trade/")
    public String list(Model model) {
        List<TradePostDto> tradePostDtoList = tradePostService.getPostlist();
        model.addAttribute("TradePostList", tradePostDtoList);
        return "list";
    }

    @GetMapping("/trade/posts")
    public String saveForm() {
        return "save.jsp";
    }

//	@GetMapping("/trade/like-posts")
//	public String saveForm2() {
//		return "save";
//	}

    @PostMapping("/trade/posts")
    public String write(Authentication authentication, TradePostDto tradePostDto) {
        try {
            if(authentication == null)
                throw new Exception("authentication is null. non user Info");

            String kakaoName = authentication.getName();
            UserResponseDto userResponseDto = userService.findByUserKakaoNickName(kakaoName);
            tradePostDto.setUser(userResponseDto.getUser());

        }catch(Exception e){
            e.getStackTrace();
        } finally {
            tradePostService.savePost(tradePostDto);
        }

        return "redirect:/";
    }

    @GetMapping("/trade/posts/{id}")
    public String detail(@PathVariable Long id, Model model) {
        TradePostDto tradePostDto = tradePostService.getPost(id);

        model.addAttribute("tradepostDto", tradePostDto);
        return "detail";
    }

    @GetMapping("/trade/posts/update/{id}")
    public String edit(@PathVariable Long id, Model model) {
        TradePostDto tradePostDto = tradePostService.getPost(id);

        model.addAttribute("tradepostDto", tradePostDto);
        return "update.html";
    }

    @PatchMapping("/trade/posts/update/{id}")
    public String update(@PathVariable Long id, TradePostDto tradePostDto, HttpServletResponse response) {
        tradePostDto.setId(id);
        tradePostService.savePost(tradePostDto);

        response.setHeader(HttpHeaders.ALLOW, "GET, POST, PUT, PATCH, DELETE");

        return "redirect:/";
    }

    @DeleteMapping("trade/posts/{id}")
    public String delete(@PathVariable Long id) {
        tradePostService.deletePost(id);

        return "redirect:/";
    }

    @GetMapping("/trade/search")
    public String search(@RequestParam String keyword, Model model) {
        List<TradePostDto> tradePostDtoList = tradePostService.searchPosts(keyword);
        model.addAttribute("TradePostList", tradePostDtoList);

        return "list";
    }

}