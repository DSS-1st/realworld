package com.dss.realworld.common.dto;

import lombok.Getter;

@Getter
public class AuthorDto {

    private final String username;
    private final String bio;
    private final String image;
    private final boolean following;

    private AuthorDto(String username, String bio, String image, boolean followed) {
        this.username = username;
        this.bio = bio;
        this.image = image;
        this.following = followed;
    }

    public static AuthorDto of(String username, String bio, String image, boolean followed) {
        return new AuthorDto(username, bio, image, followed);
    }
}