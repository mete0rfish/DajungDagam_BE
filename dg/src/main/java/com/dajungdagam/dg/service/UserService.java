package com.dajungdagam.dg.service;

import com.dajungdagam.dg.domain.Area;
import com.dajungdagam.dg.domain.RoleType;
import com.dajungdagam.dg.domain.User;
import com.dajungdagam.dg.domain.dto.UserKakaoLoginResponseDto;
import com.dajungdagam.dg.domain.dto.UserResponseDto;
import com.dajungdagam.dg.jwt.jwtTokenProvider;
import com.dajungdagam.dg.repository.AreaJpaRepository;
import com.dajungdagam.dg.repository.UserJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserJpaRepository repository;

    @Autowired
    private AreaJpaRepository areaRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    private Long expiredMs = 1000* 60 * 60l;

    private static int userIdx = 0;

    public UserKakaoLoginResponseDto kakaoLogin(Map<String, Object> userInfo) {
        String kakaoName = (String)userInfo.get("kakaoName");
        log.info("kakaoName: "+kakaoName);

        UserResponseDto userResponseDto = findByUserKakaoNickName(kakaoName);

        if(userResponseDto == null) {
            // 기존에 등록된 유저가 아니면, DB에 저장 후 다시 불러오기
            signUp(userInfo);
            userResponseDto = findByUserKakaoNickName(kakaoName);
        }

        // JWT 토큰 발행
        String token = jwtTokenProvider.createToken(kakaoName, secretKey, expiredMs);
        return new UserKakaoLoginResponseDto(HttpStatus.OK, token, userResponseDto.getUser());
    }

    public UserResponseDto findByUserKakaoNickName(String kakaoName){
        User user = repository.findByKakaoName(kakaoName);
        //log.info("user: " + user.getKakaoName());
        if(user == null){
            return null;
        }
        return new UserResponseDto(user);
    }

    @Transactional
    public int signUp(Map<String, Object> userInfo) {
        int id = 0;
        String kakaoName = (String)userInfo.get("kakaoName");
        log.info(kakaoName + " in userInfo.");
        try{
            User user = new User(0, kakaoName, RoleType.USER);
            log.info(user.getKakaoName() + " 가 저장되었습니다.");
            id = repository.save(user).getId();


        } catch(Exception e){
            System.out.println(e);
        }
        return id;
    }


    // 유저 별명 업데이트
    @Transactional
    public int updateUserNickName(String kakaoName, String nickName) {
        int id = -1;
        try{

            User user = repository.findByKakaoName(kakaoName);
            if(user == null)
                throw new Exception("닉네임을 변경할 유저의 정보가 없습니다.");

            user.setNickName(nickName);

            id = repository.save(user).getId();

        } catch(Exception e){
            e.getStackTrace();
            return id;
        }

        return id;

    }

    // 유저 사는곳 업데이트

    @Transactional
    public int updateUserArea(String kakaoName, String gu, String dong){
        int id = -1;
        try{

            UserResponseDto userResponseDto = findByUserKakaoNickName(kakaoName);
            if(userResponseDto.getUser() == null)
                throw new Exception("닉네임을 변경할 유저의 정보가 없습니다.");

            User user = userResponseDto.getUser();
            Area area = areaRepository.findByGuNameAndDongName(gu, dong);
            user.setArea(area);

            id = repository.save(user).getId();

        } catch(Exception e){
            e.getStackTrace();
        }

        return id;
    }


}
