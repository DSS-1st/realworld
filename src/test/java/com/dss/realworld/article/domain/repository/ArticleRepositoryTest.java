package com.dss.realworld.article.domain.repository;

import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.dto.GetArticleDto;
import com.dss.realworld.util.ArticleFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        Article newArticle = ArticleFixtures.createDefault();
        articleRepository.persist(newArticle);

        assertThat(newArticle.getId()).isNotNull();
    }

    @Test
    void Should_Success_When_FindByArticleId() {
        Article newArticle = ArticleFixtures.createDefault();
        articleRepository.persist(newArticle);

        GetArticleDto foundArticle = articleRepository.getById(newArticle.getId());

        assertThat(foundArticle.getTitle()).isEqualTo(newArticle.getTitle());
    }

    @Test
    void Should_Success_When_FindByArticleSlug() {
        String title = "How to train your dragon";
        String slug = "How-to-train-your-dragon-1";

        Article newArticle = ArticleFixtures.create(title, slug);
        articleRepository.persist(newArticle);

        Optional<GetArticleDto> foundArticle = articleRepository.getBySlug(newArticle.getSlug());

        Assertions.assertThat(newArticle.getSlug()).isEqualTo(foundArticle.get().getSlug());
    }

    @Test
    void Should_Success_When_ArticleIdExist() {
        Article newArticle = ArticleFixtures.createDefault();
        articleRepository.persist(newArticle);

        int deletedCount = articleRepository.delete(newArticle.getId());

        Assertions.assertThat(deletedCount).isEqualTo(1);
    }

    @Test
    void Should_Success_When_DeletedCountIsZero() {
        Article newArticle = Article.builder()
                .title("How to train your dragon")
                .description("Ever wonder how?")
                .body("You have to believe")
                .build();
        articleRepository.persist(newArticle);

        int deletedCount = articleRepository.delete(10L);

        Assertions.assertThat(deletedCount).isEqualTo(0);
    }
}