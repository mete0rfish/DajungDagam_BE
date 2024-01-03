package com.dajungdagam.dg.controller;

import com.dajungdagam.dg.domain.dto.WishlistDto;
import com.dajungdagam.dg.domain.entity.Wishlist;
import com.dajungdagam.dg.repository.WishListJpaRepository;
import com.dajungdagam.dg.service.WishlistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/wishlist")
    public ResponseEntity<String> likes(WishlistDto wishlistDto) {

        log.info(wishlistDto.getKakaoName());

        boolean res = wishlistService.addPostToWishlist(wishlistDto);
        if(!res) {
            log.info("찜하기 실패");
        }

        log.info("찜하기 성공");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/wishlist/{kakaoName}")
    public ResponseEntity<Wishlist> getWishlistByUserId(@PathVariable("kakaoName") String kakaoName){
        Wishlist wishlist = wishlistService.getWishlistByKakaoName(kakaoName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(wishlist, headers, HttpStatus.OK);
    }
}
