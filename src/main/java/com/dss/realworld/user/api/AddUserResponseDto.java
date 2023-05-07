package com.dss.realworld.user.api;

import com.dss.realworld.user.domain.repository.GetUserDto;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;

@JsonRootName(value = "user")
@Getter
public class AddUserResponseDto {

    private final String email;
    private final String token = "";
    private final String username;
    private final String bio;
    private final String image;

    AddUserResponseDto(GetUserDto getUserDto) {
        this.email = getUserDto.getEmail();
        this.username = getUserDto.getUsername();
        this.bio = getUserDto.getBio();
        this.image = getUserDto.getImage();
    }
}