package com.dajungdagam.dg.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponseDto {
    private String info;

    public UserInfoResponseDto() {
    }

    public UserInfoResponseDto(String info) {
        this.info = info;
    }
}
