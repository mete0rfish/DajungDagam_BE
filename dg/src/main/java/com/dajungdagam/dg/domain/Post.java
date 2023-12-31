package com.dajungdagam.dg.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Post {
    @Id
    @GeneratedValue
    private Long id;
}
