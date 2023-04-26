package com.dss.realworld.article.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateArticleRequestDto {

    private CreateArticleDto article;

    @Getter
    @Builder
    public static class CreateArticleDto {

        private String title;
        private String description;
        private String body;
        private Set<String> tagList;
    }
}
