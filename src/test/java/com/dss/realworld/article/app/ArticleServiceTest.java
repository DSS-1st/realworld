package com.dss.realworld.article.app;

import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.dto.GetArticleDto;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

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
    void Should_Success_When_ArticleSlugAndUserIdIsValid() {
        //given
        Long validUserId = 1L;
        Article newArticle = createArticle(validUserId);
        GetArticleDto savedArticle = articleRepository.getArticleById(newArticle.getId());
        Assertions.assertThat(savedArticle.getId()).isEqualTo(validUserId);

        //when
        articleService.deleteArticle(savedArticle.getSlug(), validUserId);
        Optional<GetArticleDto> foundArticle = Optional.ofNullable(articleRepository.getArticleById(validUserId));

        //then
        Assertions.assertThat(foundArticle).isEmpty();
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
