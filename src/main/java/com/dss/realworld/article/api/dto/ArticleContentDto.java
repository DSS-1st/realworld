package com.dss.realworld.article.api.dto;

import com.dss.realworld.article.domain.Article;
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

    public static ArticleContentDto of(Article article) {
        return new ArticleContentDto(article.getSlug(), article.getTitle(), article.getDescription(), article.getBody(), article.getCreatedAt(), article.getUpdatedAt());
    }
}
