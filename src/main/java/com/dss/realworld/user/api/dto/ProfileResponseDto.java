package com.dss.realworld.user.api.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@JsonTypeName(value = "profile")
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
@Getter
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