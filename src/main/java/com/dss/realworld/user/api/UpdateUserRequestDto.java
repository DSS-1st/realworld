package com.dss.realworld.user.api;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonRootName("user")

@Getter
@NoArgsConstructor
public class UpdateUserRequestDto {

    private String email;
    private String username;
    private String password;
    private String bio;
    private String image;

    @Builder
    public UpdateUserRequestDto(String email, String username, String password, String bio, String image) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.image = image;
    }
}
