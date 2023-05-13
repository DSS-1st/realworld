package com.dss.realworld.user.api;

import com.dss.realworld.common.dto.AuthorDto;
import com.dss.realworld.user.domain.User;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;

@Getter
@JsonRootName(value = "profile")
public class GetProfileDto {

    private final String username;
    private final String bio;
    private final String image;
    private final boolean following = false;

    private GetProfileDto(String username, String bio, String image) {
        this.username = username;
        this.bio = bio;
        this.image = image;
    }

    public static GetProfileDto of(User user) {
        return new GetProfileDto(user.getUsername(), user.getBio(), user.getImage());
    }
}