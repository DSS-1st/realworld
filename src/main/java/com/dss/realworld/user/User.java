package com.dss.realworld.user;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
public class User {

    private final Long id;
    private final String username;
    private final String password;
    private final String email;
    private final String bio;
    private final String image;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private final LocalDateTime updatedAt = LocalDateTime.now();

    @Builder
    public User(Long id,
                String username,
                String password,
                String email,
                String bio,
                String image) {
        this.id = id;
        Assert.notNull(username,"username can not be null");
        this.username = username;
        Assert.notNull(password,"password can not be null");
        this.password = password;
        Assert.notNull(email,"email can not be null");
        this.email = email;
        this.bio = bio;
        this.image = image;
    }
}
