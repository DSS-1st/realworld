package com.dss.realworld.util;

import com.dss.realworld.user.domain.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserFixtures {

    public static User create() {
        return User.builder()
                .username("new_user")
                .password("new_password")
                .passwordEncoder(new BCryptPasswordEncoder())
                .email("new_email@realworld.com")
                .build();
    }

    public static User create(String username, String password, String email) {
        return User.builder()
                .username(username)
                .password(password)
                .passwordEncoder(new BCryptPasswordEncoder())
                .email(email)
                .build();
    }

    public static User create(Long id, String username, String email, String password) {
        return User.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .passwordEncoder(new BCryptPasswordEncoder())
                .build();
    }
}