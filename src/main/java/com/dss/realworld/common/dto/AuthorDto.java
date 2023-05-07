package com.dss.realworld.common.dto;

import lombok.Getter;

@Getter
public class AuthorDto {

    private final String username;
    private final String bio;
    private final String image;
    private final boolean following = false;

    private AuthorDto(String username, String bio, String image) {
        this.username = username;
        this.bio = bio;
        this.image = image;
    }

    public static AuthorDto of(String username, String bio, String image) {
        return new AuthorDto(username, bio, image);
    }
}