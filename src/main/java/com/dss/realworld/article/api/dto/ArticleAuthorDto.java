package com.dss.realworld.article.api.dto;

import lombok.Getter;

@Getter
public class ArticleAuthorDto {

    private final String username;
    private final String bio;
    private final String image;
    private final boolean following = false;

    private ArticleAuthorDto(String username, String bio, String image) {
        this.username = username;
        this.bio = bio;
        this.image = image;
    }

    public static ArticleAuthorDto of(String username, String bio, String image) {
        return new ArticleAuthorDto(username, bio, image);
    }
}