package com.dajungdagam.dg.controller;

import com.dajungdagam.dg.domain.dto.TradePostDto;
import com.dajungdagam.dg.domain.dto.UserResponseDto;
import com.dajungdagam.dg.jwt.jwtTokenDecoder;
import com.dajungdagam.dg.service.TradePostService;
import com.dajungdagam.dg.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MypageController {

    @Autowired
    private UserService userService;

    @Autowired
    private TradePostService tradePostService;


    @PostMapping("/mypage/{user_id}/posts")
    public ResponseEntity<List> getWrittenPosts(Authentication authentication, @PathVariable("user_id") int user_id) {
        List<TradePostDto> tradePostDtoList = null;
        //try{
            //if(authentication == null)
                //throw new Exception("authentication is null");

            String kakaoName = authentication.getName();
            UserResponseDto userResponseDto = userService.findByUserKakaoNickName(kakaoName);

            // kakao Name으로 받아오기
            tradePostDtoList = tradePostService.searchPostsByUserId(user_id);
            //model.addAttribute("TradePostList", tradePostDtoList);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

            return new ResponseEntity<>(tradePostDtoList, headers, HttpStatus.OK);

//        } catch(Exception e){
//            return ResponseEntity.badRequest().build();
//        }


    }


}
