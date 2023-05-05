package com.dss.realworld.article.domain;

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
    private Long userId;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder
    public Article(Long id, String slug, String title, String description, String body, Long userId) {
        Assert.notNull(title, "title can not be null");
        Assert.notNull(description, "description can not be null");
        Assert.notNull(body, "body can not be null");

        this.id = id;
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
        this.userId = userId;
    }
}
