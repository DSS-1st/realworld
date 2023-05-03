package com.dss.realworld.article.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

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
}
