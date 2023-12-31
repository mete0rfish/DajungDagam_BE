package com.dajungdagam.dg.domain.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name="users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true, length = 40)
    @Nullable
    private String email;

    @Column(unique = true, length = 10)
    @Nullable
    private String nickName;

    @Column(unique = true, length = 10)
    @Nullable
    private String kakaoName;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="area_id")
    private Area area;

    @Column
    private RoleType role;

    @Column(length = 50)
    private String info;

    public User(int id, @Nullable String email, @Nullable String nickName, @Nullable String kakaoName, Area area, RoleType role, String info) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.kakaoName = kakaoName;
        this.area = area;
        this.role = role;
        this.info = info;
    }

    public User(int id, String kakaoName, RoleType role) {
        this.id = id;
        this.kakaoName = kakaoName;
        this.role = role;
    }

    public User() {

    }
}
