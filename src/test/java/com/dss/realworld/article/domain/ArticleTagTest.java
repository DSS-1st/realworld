package com.dss.realworld.article.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class ArticleTagTest {

    @DisplayName(value = "필수 입력값이 유효하면 ArticleTag 객체 생성 성공")
    @Test
    void t1() {
        //given
        Long articleId = 1L;
        Long tagId = 1L;

        //when
        ArticleTag articleTag = new ArticleTag(articleId, tagId);

        //then
        assertThat(articleTag.getArticleId()).isEqualTo(articleId);
        assertThat(articleTag.getTagId()).isEqualTo(tagId);
    }

    @DisplayName(value = "articleId가 null이면 예외 발생")
    @Test
    void t2() {
        //given
        Long tagId = 1L;

        //when
        //then
        assertThatIllegalArgumentException().isThrownBy(() -> new ArticleTag(null, tagId));
    }

    @DisplayName(value = "tagId가 null이면 예외 발생")
    @Test
    void t3() {
        //given
        Long articleId = 1L;

        //when
        //then
        assertThatIllegalArgumentException().isThrownBy(() -> new ArticleTag(articleId, null));
    }
}