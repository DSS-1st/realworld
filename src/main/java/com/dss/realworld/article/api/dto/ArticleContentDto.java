package com.dss.realworld.article.api.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleContentDto {

    private final String slug;
    private final String title;
    private final String description;
    private final String body;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private ArticleContentDto(final String slug, final String title, final String description, final String body, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ArticleContentDto of(final String slug, final String title, final String description, final String body, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        return new ArticleContentDto(slug, title, description, body, createdAt, updatedAt);
    }
}
