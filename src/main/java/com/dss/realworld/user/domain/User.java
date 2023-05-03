package com.dss.realworld.user.domain;

import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.type.Alias;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@Alias("User")
public class User {

    private final Long id;
    private final String username;
    private final String password;
    private final String email;
    private final String bio;
    private final String image;
    private final LocalDateTime createdAt = null;
    private final LocalDateTime updatedAt = LocalDateTime.now();

    @Builder
    public User(Long id, String username, String password, String email, String bio, String image) {
        Assert.notNull(username,"username can not be null");
        Assert.notNull(password,"password can not be null");
        Assert.notNull(email,"email can not be null");

        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.bio = bio;
        this.image = image;
    }
}
