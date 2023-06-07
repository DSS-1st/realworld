package com.dss.realworld.user.api.dto;

import com.dss.realworld.common.auth.LoginUser;
import com.dss.realworld.user.domain.User;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;

@JsonTypeName(value = "user")
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
@Getter
public class UserResponseDto {

    private final String email;
    private final String token;
    private final String username;
    private final String bio;
    private final String image;

    public UserResponseDto(User user) {
        this.email = user.getEmail();
        this.token = "";
        this.username = user.getUsername();
        this.bio = user.getBio();
        this.image = user.getImage();
    }

    public UserResponseDto(LoginUser loginUser, String token) {
        this.email = loginUser.getUser().getEmail();
        this.token = token;
        this.username = loginUser.getUser().getUsername();
        this.bio = loginUser.getUser().getBio();
        this.image = loginUser.getUser().getImage();
    }
}