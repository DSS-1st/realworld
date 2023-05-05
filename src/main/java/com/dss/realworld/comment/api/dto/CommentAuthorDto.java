package com.dss.realworld.comment.api.dto;

import com.dss.realworld.user.domain.repository.GetUserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentAuthorDto {

    private String username;
    private String bio;
    private String image;
    private boolean following;

    CommentAuthorDto(GetUserDto getUserDto) {
        this.username = getUserDto.getUsername();
        this.bio = getUserDto.getBio();
        this.image = getUserDto.getImage();
    }
}