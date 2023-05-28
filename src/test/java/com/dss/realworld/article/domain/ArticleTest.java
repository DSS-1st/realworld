package com.dss.realworld.article.domain;

import com.dss.realworld.article.api.dto.UpdateArticleRequestDto;
import com.dss.realworld.error.exception.UserNotFoundException;
import com.dss.realworld.util.ArticleFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ArticleTest {

    @DisplayName(value = "필수 입력값이 유효하면 Article 객체 생성 성공")
    @Test
    void t1() {
        Article article = Article.builder()
                .title("How to train your dragon")
                .description("Ever wonder how?")
                .body("You have to believe")
                .build();

        assertThat(article.getTitle()).isEqualTo("How to train your dragon");
        assertThat(article.getDescription()).isEqualTo("Ever wonder how?");
        assertThat(article.getBody()).isEqualTo("You have to believe");
    }

    @DisplayName(value = "title이 null이면 예외 발생")
    @Test
    void t2() {
        assertThatIllegalArgumentException().isThrownBy(() -> Article.builder()
                .description("Ever wonder how?")
                .body("You have to believe")
                .build());
    }

    @DisplayName(value = "Description이 null이면 예외 발생")
    @Test
    void t3() {
        assertThatIllegalArgumentException().isThrownBy(() -> Article.builder()
                .title("How to train your dragon")
                .body("You have to believe")
                .build());
    }

    @DisplayName(value = "Body가 null이면 예외 발생")
    @Test
    void t4() {
        assertThatIllegalArgumentException().isThrownBy(() -> Article.builder()
                .title("How to train your dragon")
                .description("Ever wonder how?")
                .build());
    }

    @DisplayName(value = "Article 수정 성공")
    @Test
    void t5() {
        //given
        Article newArticle = ArticleFixtures.of(1L, "old title");
        String newTitle = "new title";
        UpdateArticleRequestDto updateDto = new UpdateArticleRequestDto(newTitle,"","");

        //when
        newArticle.updateArticle(updateDto);

        //then
        Assertions.assertThat(newArticle.getTitle()).isEqualTo(newTitle);
        assertThat(newArticle.getSlug()).isEqualTo("new-title-1");
    }

    @DisplayName(value = "loginId가 null이면 예외 발생")
    @Test
    void t6() {
        Article newArticle = ArticleFixtures.create();
        assertThatThrownBy(() -> newArticle.isAuthorMatch(null)).isInstanceOf(UserNotFoundException.class);
    }

    @DisplayName(value = "Article의 userId가 null이면 예외 발생")
    @Test
    void t7() {
        Article newArticle = ArticleFixtures.create();
        assertThatThrownBy(() -> newArticle.isAuthorMatch(1L)).isInstanceOf(UserNotFoundException.class);
    }
}