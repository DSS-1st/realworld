package com.dss.realworld.article.app;

import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.dto.GetArticleDto;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.error.exception.ArticleAuthorNotMatchException;
import com.dss.realworld.error.exception.ArticleNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class ArticleServiceTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        clearTable();
    }

    @AfterEach
    void teatDown() {
        clearTable();
    }

    private void clearTable() {
        articleRepository.deleteAll();
        articleRepository.resetAutoIncrement();
    }

    @Test
    void Should_ThrownException_When_ArticleAuthorIsNotMatch() {
        //given
        Long validUserId = 1L;
        Article newArticle = createArticle(validUserId);
        GetArticleDto savedArticle = articleRepository.getArticleById(newArticle.getId());
        assertThat(savedArticle.getId()).isEqualTo(validUserId);

        //when
        String savedSlug = savedArticle.getSlug();
        Long wrongAuthorId = 10L;

        //then
        assertThatThrownBy(() -> articleService.deleteArticle(savedSlug, wrongAuthorId))
                .isInstanceOf(ArticleAuthorNotMatchException.class);
    }

    @Test
    void Should_ThrownException_When_ArticleIsNotFound() {
        //given
        Long validUserId = 1L;
        Article newArticle = createArticle(validUserId);
        GetArticleDto savedArticle = articleRepository.getArticleById(newArticle.getId());
        assertThat(savedArticle.getId()).isEqualTo(validUserId);

        //when
        String wrongArticleSlug = "wrongArticleSlug";

        //then
        assertThatThrownBy(() -> articleService.deleteArticle(wrongArticleSlug, validUserId))
                .isInstanceOf(ArticleNotFoundException.class);
    }

    @Test
    void Should_CreateArticleSuccess_When_ArticleDtoAndLogonIdIsValid() {
        //given
        Long logonId = 1L;
        CreateArticleRequestDto articleDto = createArticleDto();

        //when
        GetArticleDto savedArticle = articleService.createArticle(articleDto, logonId);

        //then
        assertThat(savedArticle.getUserId()).isEqualTo(logonId);
    }

    private CreateArticleRequestDto createArticleDto() {
        CreateArticleRequestDto.CreateArticleDto createArticleDto = CreateArticleRequestDto.CreateArticleDto.builder()
                .title("How to train your dragon")
                .description("Ever wonder how?")
                .body("You have to believe")
                .build();

        return new CreateArticleRequestDto(createArticleDto);
    }

    @Test
    void Should_ArticleDeleteSuccess_When_ArticleSlugAndUserIdIsValid() {
        //given
        Long validUserId = 1L;
        Article newArticle = createArticle(validUserId);
        GetArticleDto savedArticle = articleRepository.getArticleById(newArticle.getId());
        assertThat(savedArticle.getId()).isEqualTo(validUserId);

        //when
        articleService.deleteArticle(savedArticle.getSlug(), validUserId);
        Optional<GetArticleDto> foundArticle = Optional.ofNullable(articleRepository.getArticleById(validUserId));

        //then
        assertThat(foundArticle).isEmpty();
    }

    private Article createArticle(Long userId) {
        Article newArticle = Article.builder()
                .title("How to train your dragon")
                .slug("How-to-train-your-dragon-1")
                .description("Ever wonder how?")
                .body("You have to believe")
                .userId(userId)
                .build();
        articleRepository.createArticle(newArticle);

        return newArticle;
    }
}
