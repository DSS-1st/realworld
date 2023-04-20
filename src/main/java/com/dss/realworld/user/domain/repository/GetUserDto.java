package com.dss.realworld.user.domain.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Alias("GetUserDto")
public class GetUserDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String bio;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
