package com.dajungdagam.dg.controller;

import com.dajungdagam.dg.jwt.jwtTokenDecoder;
import com.dajungdagam.dg.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MypageController {

    private UserService userService;
    private jwtTokenDecoder tokenDecoderoken;

    public MypageController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/mypage/{userId}")
    public ResponseEntity<String> getWrittenPosts(Authentication authentication) {


        return ResponseEntity.ok().body(authentication.getName() + "님의 마이페이지 입니다.");
    }


}
