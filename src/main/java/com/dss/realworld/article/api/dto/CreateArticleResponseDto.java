package com.dss.realworld.article.api.dto;

import com.dss.realworld.common.dto.AuthorDto;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@JsonRootName(value = "article")
public class CreateArticleResponseDto {

    private final String slug;
    private final String title;
    private final String description;
    private final String body;
    private final Set<String> tagList = new HashSet<>(); // todo Tag 도메인 추가 후 구현
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final boolean favorited = false; // todo Following 도메인 추가 후 구현
    private final int favoritesCount = 0; // todo Following 도메인 추가 후 구현
    private final AuthorDto author;

    public CreateArticleResponseDto(ArticleContentDto content, AuthorDto author) {
        this.slug = content.getSlug();
        this.title = content.getTitle();
        this.description = content.getDescription();
        this.body = content.getBody();
        this.createdAt = content.getCreatedAt();
        this.updatedAt = content.getUpdatedAt();
        this.author = author;
    }
}