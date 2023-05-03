package com.dss.realworld.article.app;

import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.dto.GetArticleDto;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.error.exception.ArticleAuthorNotMatchException;
import com.dss.realworld.error.exception.ArticleNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @DisplayName(value = "게시글 작성자가 아닐 때 삭제 시도 시 예외 발생")
    @Test
    void t1() {
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

    @DisplayName(value = "존재하지 않는 게시글을 삭제 시도 시 예외 발생")
    @Test
    void t2() {
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

    @DisplayName(value = "필수 입력값, 로그인 ID가 유효하면 Article 작성 성공")
    @Test
    void t3() {
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

    @DisplayName(value = "slug와 작성자가 일치하면 Article 삭제 성공")
    @Test
    void t4() {
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
