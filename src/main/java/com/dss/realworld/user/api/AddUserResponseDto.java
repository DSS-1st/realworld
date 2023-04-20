package com.dss.realworld.user.api;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AddUserResponseDto {

    private final AddUserDto user;

    @Getter
    @Builder
    public static class AddUserDto {

        private final String email;
        private final String token;
        private final String username;
        private final String bio;
        private final String image;
    }

    @Builder
    public AddUserResponseDto(AddUserDto user) {
        this.user = user;
    }
}
