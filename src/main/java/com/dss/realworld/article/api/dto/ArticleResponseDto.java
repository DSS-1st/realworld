package com.dss.realworld.article.api.dto;

import com.dss.realworld.common.dto.AuthorDto;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@JsonTypeName(value = "article")
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
@Getter
public class ArticleResponseDto {

    private final String slug;
    private final String title;
    private final String description;
    private final String body;
    private final List<String> tagList;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final boolean favorited;
    private final int favoritesCount;
    private final AuthorDto author;

    public ArticleResponseDto(ArticleContentDto content, AuthorDto author, List<String> tagList, boolean favorited, int favoritesCount) {
        this.slug = content.getSlug();
        this.title = content.getTitle();
        this.description = content.getDescription();
        this.body = content.getBody();
        this.tagList = tagList;
        this.createdAt = content.getCreatedAt();
        this.updatedAt = content.getUpdatedAt();
        this.favorited = favorited;
        this.favoritesCount = favoritesCount;
        this.author = author;
    }

    public ArticleResponseDto(ArticleContentDto content, AuthorDto author, List<String> tagList) {
        this.slug = content.getSlug();
        this.title = content.getTitle();
        this.description = content.getDescription();
        this.body = content.getBody();
        this.tagList = tagList;
        this.createdAt = content.getCreatedAt();
        this.updatedAt = content.getUpdatedAt();
        this.favorited = false;
        this.favoritesCount = 0;
        this.author = author;
    }
}