package com.dss.realworld.util;

import com.dss.realworld.user.domain.User;

public class UserFixtures {

    public static User create() {
        return User.builder()
                .username("Jacob000")
                .password("jakejake")
                .email("jake000@jake.jake")
                .build();
    }

    public static User create(String username, String password, String email) {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
    }

    public static User create(Long id, String username, String email, String password) {
        return User.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
}