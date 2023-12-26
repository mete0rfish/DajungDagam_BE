package com.dajungdagam.dg.domain.dto;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class UserKakaoLoginResponseDto {

    private HttpStatus httpStatus;
    private String accessToken;
    private String nickName;

    public UserKakaoLoginResponseDto(HttpStatus httpStatus, String accessToken, String nickName) {
        this.httpStatus = httpStatus;
        this.accessToken = accessToken;
        this.nickName = nickName;
    }
}
