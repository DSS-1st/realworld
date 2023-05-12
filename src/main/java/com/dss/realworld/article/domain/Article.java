package com.dss.realworld.article.domain;

import com.dss.realworld.article.api.dto.UpdateArticleRequestDto;
import com.dss.realworld.error.exception.UserNotFoundException;
import lombok.Builder;
import lombok.Getter;
import org.apache.ibatis.type.Alias;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@Alias(value = "Article")
public class Article {

    private Long id;
    private String slug;
    private String title;
    private String description;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;

    @Builder
    public Article(Long id, String slug, String title, String description, String body, LocalDateTime createdAt, LocalDateTime updatedAt, Long userId) {
        Assert.notNull(title, "title can not be null");
        Assert.notNull(description, "description can not be null");
        Assert.notNull(body, "body can not be null");

        this.id = id;
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
    }

    public boolean isAuthorMatch(final Long loginId) {
        if(loginId == null || this.userId == null) throw new UserNotFoundException();

        return this.userId.compareTo(loginId) != 0;
    }

    public Article updateArticle(final UpdateArticleRequestDto updateArticleRequestDto) {
        this.slug = Slug.of(updateArticleRequestDto.getTitle(), this.id,false).getValue();
        this.title = updateArticleRequestDto.getTitle();
        this.description = updateArticleRequestDto.getDescription();
        this.body = updateArticleRequestDto.getBody();

        return this;
    }
}