package com.dss.realworld.article.api.dto;

import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.Slug;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@JsonTypeName(value = "article")
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
@Getter
@NoArgsConstructor
public class CreateArticleRequestDto {

    @NotBlank(message = "can't empty or space only title")
    private String title;

    @NotEmpty(message = "can't empty description")
    private String description;

    @NotBlank(message = "can't empty or space only body")
    private String body;

    private Set<String> tagList;

    @Builder
    public CreateArticleRequestDto(final String title, final String description, final String body, final List<String> tagList) {
        this.title = title;
        this.description = description;
        this.body = body;
        this.tagList = Optional.ofNullable(tagList).map(HashSet::new).orElseGet(HashSet::new);
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