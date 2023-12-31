package com.dajungdagam.dg.domain.dto;

import com.dajungdagam.dg.domain.User;
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
    private String jwtToken;
    private User user;

    public UserKakaoLoginResponseDto(HttpStatus httpStatus, String accessToken, User user) {
        this.httpStatus = httpStatus;
        this.jwtToken = accessToken;
        this.user = user;
    }
}
