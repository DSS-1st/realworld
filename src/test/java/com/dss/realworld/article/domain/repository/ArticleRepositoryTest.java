package com.dss.realworld.article.domain.repository;

import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.dto.GetArticleDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

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
    void Should_Success_When_RequiredFieldsAreNotNull() {
        Article newArticle = Article.builder()
                .title("How to train your dragon")
                .description("Ever wonder how?")
                .body("You have to believe")
                .build();
        articleRepository.createArticle(newArticle);

        assertThat(newArticle.getId()).isNotNull();
    }

    @Test
    void Should_Success_When_FindByArticleId() {
        Article newArticle = Article.builder()
                .title("How to train your dragon")
                .description("Ever wonder how?")
                .body("You have to believe")
                .build();
        articleRepository.createArticle(newArticle);

        GetArticleDto foundArticle = articleRepository.getArticleById(newArticle.getId());

        assertThat(foundArticle.getTitle()).isEqualTo(newArticle.getTitle());
    }

    @Test
    void Should_Success_When_FindByArticleSlug() {
        String title = "How to train your dragon";
        String slug = title.trim().replace(" ", "-") + "-1";

        Article newArticle = Article.builder()
                .title(title)
                .slug(slug)
                .description("Ever wonder how?")
                .body("You have to believe")
                .build();

        articleRepository.createArticle(newArticle);

        GetArticleDto foundArticle = articleRepository.getArticleBySlug(newArticle.getSlug());

        Assertions.assertThat(newArticle.getSlug()).isEqualTo(foundArticle.getSlug());
    }
}