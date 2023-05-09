package com.dss.realworld.article.api.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonRootName(value = "article")
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