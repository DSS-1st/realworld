package com.dss.realworld.article.api.dto;

import com.dss.realworld.article.domain.dto.GetArticleDto;
import com.dss.realworld.user.domain.repository.GetUserDto;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@JsonRootName(value = "article")
@NoArgsConstructor
public class CreateArticleResponseDto {

    private String slug;
    private String title;
    private String description;
    private String body;
    private Set<String> tagList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean favorited;
    private int favoritesCount;
    private ArticleAuthorDto author;

    public CreateArticleResponseDto(GetArticleDto getArticleDto, GetUserDto getUserDto) {
        this.slug = getArticleDto.getSlug();
        this.title = getArticleDto.getTitle();
        this.description = getArticleDto.getDescription();
        this.body = getArticleDto.getBody();
        this.tagList = new HashSet<>();
        this.createdAt = getArticleDto.getCreatedAt();
        this.updatedAt = getArticleDto.getUpdatedAt();
        this.author = new ArticleAuthorDto(getUserDto);
    }
}
