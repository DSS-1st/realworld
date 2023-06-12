package com.dss.realworld.util;

import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.ArticleTag;
import com.dss.realworld.article.domain.ArticleUsers;
import com.dss.realworld.article.domain.Slug;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.article.domain.repository.ArticleTagRepository;
import com.dss.realworld.article.domain.repository.ArticleUsersRepository;

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

    public static void saveArticleSample(final ArticleRepository articleRepository, final int midRange, final int endRange, final Long midRangeAuthorId, final Long endRangeAuthorId) {
        String title = "test sample title ";

        for (int i = 1; i < midRange; i++) {
            Long articleId = articleRepository.findMaxId().orElse(0L) + 1;
            Article article = of(title + i, Slug.of(title, articleId).getValue(), midRangeAuthorId);
            articleRepository.persist(article);
        }

        for (int i = midRange; i <= endRange; i++) {
            Long articleId = articleRepository.findMaxId().orElse(0L) + 1;
            Article article = of(title + i, Slug.of(title, articleId).getValue(), endRangeAuthorId);
            articleRepository.persist(article);
        }
    }

    public static void saveArticleTagSample(final ArticleRepository articleRepository,
                                            final ArticleTagRepository articleTagRepository,
                                            int midRange, int endRange,
                                            final Long midRangeAuthorId, final Long endRangeAuthorId,
                                            final Long midRangeTagId, final Long endRangeTagId) {
        String title = "test sample title ";

        for (int i = 1; i < midRange; i++) {
            Long articleId = articleRepository.findMaxId().orElse(0L) + 1;
            Article article = of(title + i, Slug.of(title, articleId).getValue(), midRangeAuthorId);
            articleRepository.persist(article);
            articleTagRepository.persist(new ArticleTag(article.getId(), midRangeTagId));
        }

        for (int i = midRange; i <= endRange; i++) {
            Long articleId = articleRepository.findMaxId().orElse(0L) + 1;
            Article article = of(title + i, Slug.of(title, articleId).getValue(), endRangeAuthorId);
            articleRepository.persist(article);
            articleTagRepository.persist(new ArticleTag(article.getId(), endRangeTagId));
        }
    }

    public static void saveArticleUsersSample(final ArticleRepository articleRepository,
                                              final ArticleUsersRepository articleUsersRepository,
                                              int midRange, int endRange,
                                              final Long midRangeAuthorId, final Long endRangeAuthorId,
                                              final Long midRangeFavoritedBy, final Long endRangeFavoritedBy) {
        String title = "test sample title ";

        for (int i = 1; i < midRange; i++) {
            Long articleId = articleRepository.findMaxId().orElse(0L) + 1;
            Article article = of(title + i, Slug.of(title, articleId).getValue(), midRangeAuthorId);
            articleRepository.persist(article);
            articleUsersRepository.persist(new ArticleUsers(article.getId(), midRangeFavoritedBy));
        }

        for (int i = midRange; i <= endRange; i++) {
            Long articleId = articleRepository.findMaxId().orElse(0L) + 1;
            Article article = of(title + i, Slug.of(title, articleId).getValue(), endRangeAuthorId);
            articleRepository.persist(article);
            articleUsersRepository.persist(new ArticleUsers(article.getId(), endRangeFavoritedBy));
        }
    }
}