package com.dajungdagam.dg.domain.dto;

import com.dajungdagam.dg.domain.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
public class UserMypageInfoResponseDto {
    private User user;

    private HttpStatus httpStatus;

    public UserMypageInfoResponseDto(User user, HttpStatus httpStatus) {
        this.user = user;
        this.httpStatus = httpStatus;
    }
}
