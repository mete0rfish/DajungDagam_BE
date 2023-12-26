package com.dajungdagam.dg.service;

import com.dajungdagam.dg.domain.User;
import com.dajungdagam.dg.domain.dto.UserKakaoLoginResponseDto;
import com.dajungdagam.dg.domain.dto.UserResponseDto;
import com.dajungdagam.dg.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO DB에 user정보가 존재하면 DB에 가입하는 절차 -> 했든 안했든 JWT 토큰 발행
// https://jules-jc.tistory.com/239
@Service
public class UserService {

    @Autowired
    private UserJpaRepository repository;

    public UserKakaoLoginResponseDto kakaoLogin(String nickName){
        UserResponseDto userResponseDto = findByUserKakaoNickName(nickName);
        if(userResponseDto == null) {
            signUp(userResponseDto)
        }

    }

    public UserResponseDto findByUserKakaoNickName(String kakaoNickName){
        User user = repository.findByNickName(kakaoNickName);
        if(user == null){
            return null;
        }
        return new UserResponseDto(user);
    }
}
