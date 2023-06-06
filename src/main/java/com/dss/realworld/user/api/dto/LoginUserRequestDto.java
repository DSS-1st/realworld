package com.dss.realworld.user.api.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonTypeName(value = "user")
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
@Getter
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