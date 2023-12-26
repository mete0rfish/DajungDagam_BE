package com.dajungdagam.dg.domain.dto;

import com.dajungdagam.dg.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {

    private User user;

    public UserResponseDto(User user) {
        this.user = user;
    }
}
