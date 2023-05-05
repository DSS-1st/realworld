package com.dss.realworld.user.api;

import com.dss.realworld.user.domain.repository.GetUserDto;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonRootName(value = "user")
@Getter
@NoArgsConstructor
public class AddUserResponseDto {

    private String email;
    private String token;
    private String username;
    private String bio;
    private String image;

    AddUserResponseDto(GetUserDto getUserDto) {
        this.email = getUserDto.getEmail();
        this.username = getUserDto.getUsername();
        this.bio = getUserDto.getBio();
        this.image = getUserDto.getImage();
    }
}