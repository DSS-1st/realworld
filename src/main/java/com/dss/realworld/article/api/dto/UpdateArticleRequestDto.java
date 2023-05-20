package com.dss.realworld.article.api.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonTypeName(value = "article")
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
@NoArgsConstructor
public class UpdateArticleRequestDto {

    private String title;
    private String description;
    private String body;

    @Builder
    public UpdateArticleRequestDto(final String title, final String description, final String body) {
        this.title = title;
        this.description = description;
        this.body = body;
    }
}