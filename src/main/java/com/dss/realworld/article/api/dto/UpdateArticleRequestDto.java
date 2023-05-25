package com.dss.realworld.article.api.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonTypeName(value = "article")
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
@Getter
@NoArgsConstructor
public class UpdateArticleRequestDto {

    @NotBlank(message = "can't empty or space only title")
    private String title;

    @NotEmpty(message = "can't empty description")
    private String description;

    @NotBlank(message = "can't empty or space only body")
    private String body;

    @Builder
    public UpdateArticleRequestDto(final String title, final String description, final String body) {
        this.title = title;
        this.description = description;
        this.body = body;
    }
}