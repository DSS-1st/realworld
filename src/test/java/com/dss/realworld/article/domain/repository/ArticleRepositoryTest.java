package com.dss.realworld.article.domain.repository;

import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.dto.GetArticleDto;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import org.assertj.core.api.Assertions;
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

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void createDefaultUser() {
        User newUser = User.builder()
                .username("Jacob000")
                .email("jake000@jake.jake")
                .password("jakejake")
                .build();
        userRepository.addUser(newUser);
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
        String slug = title.trim().replace(" ", "-");

        Article newArticle = Article.builder()
                .title(title)
                .slug(slug)
                .description("Ever wonder how?")
                .body("You have to believe")
                .build();

        articleRepository.createArticle(newArticle);

        GetArticleDto foundArticle = articleRepository.getArticleById(newArticle.getId());

        Assertions.assertThat(newArticle.getSlug()).isEqualTo(foundArticle.getSlug());
    }
}