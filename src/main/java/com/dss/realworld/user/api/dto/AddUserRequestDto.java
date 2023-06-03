package com.dss.realworld.user.api.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@JsonTypeName(value = "user")
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
@Getter
@NoArgsConstructor
public class AddUserRequestDto {

    @NotBlank(message = "can't empty or space only username")
    private String username;

    @NotBlank(message = "can't empty or space only email")
    @Email
    private String email;

    @NotBlank(message = "can't empty or space only password")
    private String password;

    @Builder
    public AddUserRequestDto(final String username, final String email, final String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}