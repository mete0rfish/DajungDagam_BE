package com.dajungdagam.dg.controller;

import com.dajungdagam.dg.domain.dto.UserResponseDto;
import com.dajungdagam.dg.domain.dto.WishlistDto;
import com.dajungdagam.dg.domain.entity.Wishlist;
import com.dajungdagam.dg.repository.WishListJpaRepository;
import com.dajungdagam.dg.service.UserService;
import com.dajungdagam.dg.service.WishlistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private UserService userService;

    @PostMapping("/wishlist")
    public ResponseEntity<Wishlist> likes(@RequestBody WishlistDto wishlistDto) {

        log.info(wishlistDto.getKakaoName());

        Wishlist wishlist = wishlistService.addPostToWishlist(wishlistDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        if(wishlist == null) {
            log.info("찜하기 실패");
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }

        log.info("찜하기 성공");
        log.info("wishlist: " + wishlist.toString());

        return new ResponseEntity<>(wishlist, headers, HttpStatus.OK);
    }

    @DeleteMapping("/wishlist")
    public ResponseEntity<Wishlist> deletePost(Authentication authentication,
                                               @RequestParam int postCategory, @RequestParam Long postId){
        Wishlist wishlist = null;
        try{
            if(authentication == null)  throw new Exception("authentication is null");

            String kakaoName = authentication.getName();
            wishlist = wishlistService.deletePostAtWishlist(kakaoName, postCategory, postId);

            log.info("찜목록 게시글 삭제됨.");
            log.info(wishlist.toString());

        } catch (Exception e){
            log.error(e.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        log.info("wishlist: " + wishlist.toString());

        return new ResponseEntity<>(wishlist, headers, HttpStatus.OK);

    }

    @PostMapping("/wishlist/{kakaoName}")
    public ResponseEntity<Wishlist> getWishlistByUserId(@PathVariable("kakaoName") String kakaoName){
        Wishlist wishlist = wishlistService.getWishlistByKakaoName(kakaoName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        log.info("wishlist: " + wishlist.toString());

        return new ResponseEntity<>(wishlist, headers, HttpStatus.OK);
    }
}
