package com.dss.realworld.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AddUserRequestDto {

    private AddUserDto user;
    @Getter
    @Builder
    public static class AddUserDto {

        private String username;
        private String email;
        private String password;
    }
}
