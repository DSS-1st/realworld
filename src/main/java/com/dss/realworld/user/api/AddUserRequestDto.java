package com.dss.realworld.user.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
