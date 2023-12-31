package com.dajungdagam.dg.domain.dto;

import com.dajungdagam.dg.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.http.HttpStatus;


@Getter
@Setter
public class UserMypageInfoResponseDto {
    private User user;

    private HttpStatus httpStatus;

}
