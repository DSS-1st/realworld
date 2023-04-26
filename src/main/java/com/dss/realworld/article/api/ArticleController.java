package com.dss.realworld.article.api;

import com.dss.realworld.article.app.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public CreateArticleResponseDto createArticle(@RequestBody CreateArticleRequestDto createArticleRequestDto) {
        return null;
    }
}
