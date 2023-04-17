package com.dss.realworld.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddUserRequestDto {
    private AddUserDto user;
    @Getter
    @Builder
    public static class AddUserDto {
        @NotBlank(message = "can not be empty")
        private String username;

        @Email(message = "should be an email")
        private String email;

        @NotBlank(message = "can not be empty")
        private String password;
    }


    //todo 테이블 클래스로 전환하는 로직 도입
}
