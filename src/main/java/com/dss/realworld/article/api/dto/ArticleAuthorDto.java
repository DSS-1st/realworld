package com.dss.realworld.article.api.dto;

import com.dss.realworld.user.domain.repository.GetUserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleAuthorDto {

    private String username;
    private String bio;
    private String image;
    private boolean following;

    ArticleAuthorDto(final GetUserDto getUserDto) {
        this.username = getUserDto.getUsername();
        this.bio = getUserDto.getBio();
        this.image = getUserDto.getImage();
    }
}
