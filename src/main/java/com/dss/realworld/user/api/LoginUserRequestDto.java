package com.dss.realworld.user.api;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonRootName(value = "user")
@NoArgsConstructor
public class LoginUserRequestDto {

    private String email;
    private String password;

    @Builder
    public LoginUserRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}