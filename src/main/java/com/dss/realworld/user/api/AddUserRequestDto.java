package com.dss.realworld.user.api;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonRootName("user")
@Getter
@NoArgsConstructor
public class AddUserRequestDto {

    private String username;
    private String email;
    private String password;

    @Builder
    public AddUserRequestDto(final String username, final String email, final String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
