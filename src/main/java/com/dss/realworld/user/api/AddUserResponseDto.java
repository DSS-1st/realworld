package com.dss.realworld.user.api;

import com.dss.realworld.user.domain.repository.GetUserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class AddUserResponseDto {

    private AddUserDto user;

    @Getter
    @AllArgsConstructor
    public class AddUserDto {

        private String email;
        private String token;
        private String username;
        private String bio;
        private String image;
    }

    AddUserResponseDto getAddUserResponseDto(GetUserDto getUserDto) {
        String email = getUserDto.getEmail();
        String username = getUserDto.getUsername();
        String bio = getUserDto.getBio();
        String image = getUserDto.getImage();
        AddUserDto addUserDto = new AddUserDto(email,"token",username,bio,image);
        user = addUserDto;

        return this;
    }
}

