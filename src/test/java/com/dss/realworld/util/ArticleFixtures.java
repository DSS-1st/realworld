package com.dss.realworld.util;

import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.domain.Article;

import java.util.List;

public class ArticleFixtures {

    public static Article create() {
        return Article.builder()
                .title("How to train your dragon")
                .description("Ever wonder how?")
                .body("You have to believe")
                .build();
    }

    public static Article of(Long userId) {
        return Article.builder()
                .title("How to train your dragon")
                .slug("How-to-train-your-dragon-1")
                .description("Ever wonder how?")
                .body("You have to believe")
                .userId(userId)
                .build();
    }

    public static Article of(Long articleId, String title) {
        return Article.builder()
                .id(articleId)
                .title(title)
                .description("Ever wonder how?")
                .body("You have to believe")
                .build();
    }

    public static Article of(String slug, Long userId) {
        return Article.builder()
                .title("How to train your dragon")
                .slug(slug)
                .description("Ever wonder how?")
                .body("You have to believe")
                .userId(userId)
                .build();
    }

    public static Article of(String title, String slug, Long userId) {
        return Article.builder()
                .title(title)
                .slug(slug)
                .description("Ever wonder how?")
                .body("You have to believe")
                .userId(userId)
                .build();
    }

    public static CreateArticleRequestDto createRequestDto() {
        return CreateArticleRequestDto.builder()
                .title("How to train your dragon")
                .description("Ever wonder how?")
                .body("You have to believe")
                .build();
    }

    public static CreateArticleRequestDto createRequestDto(String title, String description, String body) {
        return CreateArticleRequestDto.builder()
                .title(title)
                .description(description)
                .body(body)
                .build();
    }

    public static CreateArticleRequestDto createRequestDto(String title, String description, String body, List<String> tagList) {
        return CreateArticleRequestDto.builder()
                .title(title)
                .description(description)
                .body(body)
                .tagList(tagList)
                .build();
    }
}