package com.dss.realworld.comment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class CommentTest {

    @DisplayName(value = "body, userId, articleId가 NotNull이면 comment 생성 성공")
    @Test
    void t1() {
        Comment comment = Comment.builder()
                .body("His name was my name too.")
                .userId(1L)
                .articleId(1L)
                .build();

        assertThat(comment.getBody()).isEqualTo("His name was my name too.");
    }

    @DisplayName(value = "body값이 Null이면 예외 발생")
    @Test
    void t2() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            Comment.builder()
                    .userId(1L)
                    .articleId(1L)
                    .build();
        });
    }

    @DisplayName(value = "userId가 Null이면 예외 발생")
    @Test
    void t3() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            Comment.builder()
                    .body("His name was my name too.")
                    .articleId(1L)
                    .build();
        });
    }

    @DisplayName(value = "articleId가 Null이면 예외 발생")
    @Test
    void t4() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            Comment.builder()
                    .body("His name was my name too.")
                    .userId(1L)
                    .build();
        });
    }


}