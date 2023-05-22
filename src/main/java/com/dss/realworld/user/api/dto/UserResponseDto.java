package com.dss.realworld.user.api.dto;

import com.dss.realworld.user.domain.User;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;

@JsonRootName(value = "user")
@Getter
public class UserResponseDto {

    private final String email;
    private final String token = "";
    private final String username;
    private final String bio;
    private final String image;

    public UserResponseDto(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.bio = user.getBio();
        this.image = user.getImage();
    }
}