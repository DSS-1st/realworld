package com.dss.realworld.user.api;

import com.dss.realworld.user.domain.repository.GetUserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class AddUserResponseDto {

    private final AddUserDto user;

    @Getter
    @AllArgsConstructor
    public static class AddUserDto {

        private String email;
        private String token;
        private String username;
        private String bio;
        private String image;
    }

    AddUserResponseDto(GetUserDto getUserDto) {
        String email = getUserDto.getEmail();
        String username = getUserDto.getUsername();
        String bio = getUserDto.getBio();
        String image = getUserDto.getImage();
        user = new AddUserDto(email,"token",username,bio,image);
    }
}

