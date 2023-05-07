package com.dss.realworld.article.api.dto;

import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.Slug;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;

import java.util.Set;

@Getter
@JsonRootName(value = "article")
@NoArgsConstructor
public class CreateArticleRequestDto {

    private String title;
    private String description;
    private String body;
    private Set<String> tagList;

    @Builder
    public CreateArticleRequestDto(final String title, final String description, final String body, final Set<String> tagList) {
        this.title = title;
        this.description = description;
        this.body = body;
        this.tagList = tagList;
    }

    public Article convert(Long logonUserId, Long maxArticleId) {
        return Article.builder()
                .slug(Slug.of(this.title, maxArticleId).getString())
                .title(this.title.trim())
                .description(this.description)
                .body(this.body)
                .userId(logonUserId)
                .build();
    }
}
