package com.dss.realworld.util;

import com.dss.realworld.article.domain.Article;

import static com.dss.realworld.article.api.dto.CreateArticleRequestDto.*;

public class ArticleFixtures {

    public static Article createDefault() {
        return Article.builder()
                .title("How to train your dragon")
                .description("Ever wonder how?")
                .body("You have to believe")
                .build();
    }

    public static Article create(Long userId) {
        return Article.builder()
                .title("How to train your dragon")
                .slug("How-to-train-your-dragon-1")
                .description("Ever wonder how?")
                .body("You have to believe")
                .userId(userId)
                .build();
    }

    public static Article create(String title, String slug) {
        return Article.builder()
                .title(title)
                .slug(slug)
                .description("Ever wonder how?")
                .body("You have to believe")
                .build();
    }

    public static CreateArticleDto createRequestDto(){
        return CreateArticleDto.builder()
                .title("How to train your dragon")
                .description("Ever wonder how?")
                .body("You have to believe")
                .build();
    }

    public static CreateArticleDto createRequestDto(String title, String description, String body){
        return CreateArticleDto.builder()
                .title(title)
                .description(description)
                .body(body)
                .build();
    }
}
