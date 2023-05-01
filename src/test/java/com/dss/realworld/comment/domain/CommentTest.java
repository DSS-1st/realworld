package com.dss.realworld.comment.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class CommentTest {

    @Test
    void Should_Success_When_FieldsAreNotNull() {
        Comment comment = Comment.builder()
                .body("His name was my name too.")
                .userId(1L)
                .articleId(1L)
                .build();

        assertThat(comment.getBody()).isEqualTo("His name was my name too.");
    }

    @Test
    void Should_ThrowException_When_BodyIsNull() {
        assertThatIllegalArgumentException().isThrownBy(() -> Comment.builder()
                .userId(1L)
                .articleId(1L)
                .build());
    }

    @Test
    void Should_ThrowException_When_UserIdIdIsNull() {
        assertThatIllegalArgumentException().isThrownBy(() -> Comment.builder()
                .body("His name was my name too.")
                .articleId(1L)
                .build());
    }

    @Test
    void Should_ThrowException_When_ArticleIdIsNull() {
        assertThatIllegalArgumentException().isThrownBy(() -> Comment.builder()
                .body("His name was my name too.")
                .userId(1L)
                .build());
    }


}