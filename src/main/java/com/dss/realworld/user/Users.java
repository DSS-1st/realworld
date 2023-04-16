package com.dss.realworld.user;

import lombok.Builder;
import lombok.Getter;

//todo token 필드 추가
@Getter
public class Users {
    private final Long userId;
    private final String username;
    private final String password;
    private final String email;
    private final String bio;
    private final String image;

    @Builder
    public Users(Long userId,
                 String username,
                 String password,
                 String email,
                 String bio,
                 String image) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.bio = bio;
        this.image = image;
    }
}
