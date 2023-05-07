package com.dss.realworld.comment.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentAuthorDto {

    private String username;
    private String bio;
    private String image;
    private boolean following;

    CommentAuthorDto(String username, String bio, String image) {
        this.username = username;
        this.bio = bio;
        this.image = image;
    }
}