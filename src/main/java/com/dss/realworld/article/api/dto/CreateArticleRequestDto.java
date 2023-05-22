package com.dss.realworld.article.api.dto;

import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.Slug;
import com.dss.realworld.tag.domain.Tag;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@JsonTypeName(value = "article")
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
@Getter
@NoArgsConstructor
public class CreateArticleRequestDto {

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private String body;

    private Set<Tag> tags;

    @Builder
    public CreateArticleRequestDto(final String title, final String description, final String body, final Set<Tag> tags) {
        this.title = title;
        this.description = description;
        this.body = body;
        this.tags = tags;
    }

    public Article convert(Long logonUserId, Long articleId) {
        return Article.builder()
                .slug(Slug.of(this.title, articleId).getValue())
                .title(this.title.trim())
                .description(this.description)
                .body(this.body)
                .userId(logonUserId)
                .build();
    }
}