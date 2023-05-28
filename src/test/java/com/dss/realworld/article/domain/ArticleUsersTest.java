package com.dss.realworld.article.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class ArticleUsersTest {

    @DisplayName(value = "필수 입력값이 유효하면 ArticleUsers 객체 생성 성공")
    @Test
    void t1() {
        //given
        Long articleId = 1L;
        Long favoritedId = 1L;

        //when
        ArticleUsers articleUsers = new ArticleUsers(articleId, favoritedId);

        //then
        assertThat(articleUsers.getArticleId()).isEqualTo(articleId);
        assertThat(articleUsers.getFavoritedId()).isEqualTo(favoritedId);
    }

    @DisplayName(value = "articleId가 null이면 예외 발생")
    @Test
    void t2() {
        //given
        Long loginId = 1L;

        //when
        //then
        assertThatIllegalArgumentException().isThrownBy(() -> new ArticleUsers(null, loginId));
    }

    @DisplayName(value = "favoritedId가 null이면 예외 발생")
    @Test
    void t3() {
        //given
        Long favoritedId = 1L;

        //when
        //then
        assertThatIllegalArgumentException().isThrownBy(() -> new ArticleUsers(favoritedId, null));
    }
}