package com.dss.realworld.user;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

//todo token 필드 추가
@Getter
public class Users {
    private final Long userId;
    private final String username;
    private final String password;
    private final String email;
    private final String bio;
    private final String image;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder
    public Users(Long userId,
                 String username,
                 String password,
                 String email,
                 String bio,
                 String image,
                 LocalDateTime createdAt,
                 LocalDateTime updatedAt) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.bio = bio;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
