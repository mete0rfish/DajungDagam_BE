package com.dajungdagam.dg.domain.dto;

import com.dajungdagam.dg.domain.Post;
import com.dajungdagam.dg.domain.User;
import org.apache.http.HttpStatus;

import java.util.ArrayList;

public class UserWrittenPostDto {
    private ArrayList<Post> post;
    private User user;
    private HttpStatus httpStatus;
}
