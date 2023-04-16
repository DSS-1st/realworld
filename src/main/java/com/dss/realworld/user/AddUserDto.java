package com.dss.realworld.user;

import lombok.Getter;

@Getter
public class AddUserDto {
    private final String username;
    private final String email;
    private final String password;

    public AddUserDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    //todo 테이블 클래스로 전환하는 로직 도입
}
