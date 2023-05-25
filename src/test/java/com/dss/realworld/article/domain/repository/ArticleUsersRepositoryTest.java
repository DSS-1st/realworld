package com.dss.realworld.article.domain.repository;

import com.dss.realworld.article.domain.ArticleUsers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(value = "test")
@Sql(value = {"classpath:db/teardown.sql", "classpath:db/dataSetup.sql"})
@SpringBootTest
@Transactional
public class ArticleUsersRepositoryTest {

    @Autowired
    private ArticleUsersRepository articleUsersRepository;

    @DisplayName(value = "ArticleUsers 객체가 유효하면 저장 성공")
    @Test
    void t1() {
        //given
        Long articleId = 1L;
        Long loginId = 1L;
        ArticleUsers articleUsers = new ArticleUsers(articleId, loginId);

        //when
        articleUsersRepository.persist(articleUsers);

        //then
        ArticleUsers foundArticleUsers = articleUsersRepository.findById(articleUsers.getId()).get();
        assertThat(foundArticleUsers.getId()).isEqualTo(1);
    }

    @DisplayName(value = "ArticleUsers 객체가 유효하면 삭제 성공")
    @Test
    void t2() {
        //given
        Long articleId = 1L;
        Long loginId = 1L;
        ArticleUsers articleUsers = new ArticleUsers(articleId, loginId);

        //when
        articleUsersRepository.persist(articleUsers);

        //then
        int result = articleUsersRepository.delete(articleId, loginId);
        assertThat(result).isEqualTo(1);
    }

    @DisplayName(value = "Article이 유효하면 ArticleId로 좋아요 합계 조회 성공")
    @Test
    void t3() {
        //given
        Long articleId = 1L;
        Long loginId = 1L;
        Long loginId2 = 2L;
        ArticleUsers articleUsers = new ArticleUsers(articleId, loginId);
        ArticleUsers articleUsers2 = new ArticleUsers(articleId, loginId2);

        //when
        articleUsersRepository.persist(articleUsers);
        articleUsersRepository.persist(articleUsers2);

        //then
        int result = articleUsersRepository.findCountByArticleId(articleId);
        assertThat(result).isEqualTo(2);
    }

    @DisplayName(value = "articleId, loginId가 유효하면 조회 성공")
    @Test
    void t4() {
        //given
        Long articleId = 1L;
        Long loginId = 1L;
        Long loginId2 = 2L;
        ArticleUsers articleUsers = new ArticleUsers(articleId, loginId);
        ArticleUsers articleUsers2 = new ArticleUsers(articleId, loginId2);

        //when
        articleUsersRepository.persist(articleUsers);
        articleUsersRepository.persist(articleUsers2);

        //then
        int result = articleUsersRepository.isFavorite(articleId, loginId2);
        assertThat(result).isEqualTo(1);
    }
}