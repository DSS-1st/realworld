package com.dss.realworld.user.domain;

import com.dss.realworld.user.api.dto.LoginUserRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Alias(value = "User")
@Getter
@NoArgsConstructor
public class User {

    private Long id;
    private String email;
    private String username;
    private String password;
    private String bio;
    private String image;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder
    public User(Long id, String email, String username, String password, BCryptPasswordEncoder passwordEncoder, String bio, String image) {
        Assert.notNull(username, "username can not be null");
        Assert.notNull(password, "password can not be null");
        Assert.notNull(email, "email can not be null");
        Assert.notNull(passwordEncoder, "passwordEncoder can not be null");

        this.id = id;
        this.email = email;
        this.username = username;
        this.password = passwordEncoder.encode(password);
        this.bio = bio;
        this.image = image;
    }

    public boolean isMatch(LoginUserRequestDto loginUserRequestDto, BCryptPasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginUserRequestDto.getPassword(), this.password);
    }
}