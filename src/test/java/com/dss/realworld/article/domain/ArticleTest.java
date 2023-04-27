package com.dss.realworld.article.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class ArticleTest {

    @Test
    void Should_Success_When_FieldsAreNotNull() {
        Article article = Article.builder()
                .title("How to train your dragon")
                .description("Ever wonder how?")
                .body("You have to believe")
                .build();

        assertThat(article.getTitle()).isEqualTo("How to train your dragon");
        assertThat(article.getDescription()).isEqualTo("Ever wonder how?");
        assertThat(article.getBody()).isEqualTo("You have to believe");
    }

    @Test
    void Should_ThrowException_When_TitleIsNull() {
        assertThatIllegalArgumentException().isThrownBy(() -> Article.builder()
                .description("Ever wonder how?")
                .body("You have to believe")
                .build());
    }

    @Test
    void Should_ThrowException_When_DescriptionIsNull() {
        assertThatIllegalArgumentException().isThrownBy(() -> Article.builder()
                .title("How to train your dragon")
                .body("You have to believe")
                .build());
    }

    @Test
    void Should_ThrowException_When_BodyIsNull() {
        assertThatIllegalArgumentException().isThrownBy(() -> Article.builder()
                .title("How to train your dragon")
                .description("Ever wonder how?")
                .build());
    }
}
