package com.dss.realworld.user.api.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@JsonTypeName(value = "user")
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
@Getter
@NoArgsConstructor
public class UpdateUserRequestDto {

    @NotBlank(message = "can't empty or space only")
    @Email
    private String email;

    @NotBlank(message = "can't empty or space only")
    private String username;

    @NotBlank(message = "can't empty or space only")
    @Length(min = 8, max = 16)
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