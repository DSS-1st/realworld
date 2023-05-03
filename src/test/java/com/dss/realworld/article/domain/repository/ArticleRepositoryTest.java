package com.dss.realworld.article.domain.repository;

import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.dto.GetArticleDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @DisplayName(value = "필수 입력값이 NotNull이면 Article 생성 성공")
    @Test
    void t1() {
        Article newArticle = Article.builder()
                .title("How to train your dragon")
                .description("Ever wonder how?")
                .body("You have to believe")
                .build();
        articleRepository.createArticle(newArticle);

        assertThat(newArticle.getId()).isNotNull();
    }

    @DisplayName(value = "Article 생성 후 DB에 부여된 PK로 조회 시 성공")
    @Test
    void t2() {
        Article newArticle = Article.builder()
                .title("How to train your dragon")
                .description("Ever wonder how?")
                .body("You have to believe")
                .build();
        articleRepository.createArticle(newArticle);

        GetArticleDto foundArticle = articleRepository.getArticleById(newArticle.getId());

        assertThat(foundArticle.getTitle()).isEqualTo(newArticle.getTitle());
    }

    @DisplayName(value = "Article 생성 후 slug로 조회 시 성공")
    @Test
    void t3() {
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

    @DisplayName(value = "Article Id가 유효하면 삭제 성공")
    @Test
    void t4() {
        Article newArticle = Article.builder()
                .title("How to train your dragon")
                .description("Ever wonder how?")
                .body("You have to believe")
                .build();
        articleRepository.createArticle(newArticle);

        int deletedCount = articleRepository.deleteArticle(newArticle.getId());

        Assertions.assertThat(deletedCount).isEqualTo(1);
    }

    @DisplayName(value = "유효하지 않은 Id로 삭제 요청 시 삭제된 Article 0개")
    @Test
    void t5() {
        Article newArticle = Article.builder()
                .title("How to train your dragon")
                .description("Ever wonder how?")
                .body("You have to believe")
                .build();
        articleRepository.createArticle(newArticle);

        int deletedCount = articleRepository.deleteArticle(10L);

        Assertions.assertThat(deletedCount).isEqualTo(0);
    }
}