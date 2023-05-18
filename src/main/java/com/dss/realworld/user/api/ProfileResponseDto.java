package com.dss.realworld.user.api;

import com.dss.realworld.user.domain.User;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonRootName(value = "profile")
public class ProfileResponseDto {

    private final String username;
    private final String bio;
    private final String image;
    private final boolean following ;

    @Builder
    private ProfileResponseDto(String username, String bio, String image ,boolean following) {
        this.username = username;
        this.bio = bio;
        this.image = image;
        this.following = following;
    }
}