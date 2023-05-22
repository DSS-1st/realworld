package com.dss.realworld.article.domain.repository;

import com.dss.realworld.article.domain.ArticleTag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(value = "test")
@Sql(value = {"classpath:db/teardown.sql","classpath:db/dataSetup.sql"})
@SpringBootTest
@Transactional
public class ArticleTagRepositoryTest {

    @Autowired
    private ArticleTagRepository articleTagRepository;

    @DisplayName(value = "Article, Tag 존재 시 insert 성공")
    @Test
    void t1() {
        //given
        Long articleId = 1L;
        Long tagId = 1L;

        ArticleTag articleTag = new ArticleTag(articleId, tagId);

        //when
        articleTagRepository.persist(articleTag);

        //then
        assertThat(articleTag.getId()).isNotNull();
    }

    @DisplayName(value = "articleTagId로 조회 성공")
    @Test
    void t2() {
        //given
        Long articleId = 1L;
        Long tagId = 1L;

        ArticleTag articleTag = new ArticleTag(articleId, tagId);
        articleTagRepository.persist(articleTag);

        //when
        ArticleTag foundArticleTag = articleTagRepository.findById(articleTag.getId());

        //then
        assertThat(foundArticleTag.getId()).isEqualTo(articleTag.getId());
    }

    @DisplayName(value = "articleTag_Id로 delete 성공")
    @Test
    void t3() {
        //given
        Long articleId = 1L;
        Long tagId = 1L;

        ArticleTag articleTag = new ArticleTag(articleId, tagId);
        articleTagRepository.persist(articleTag);

        //when
        int result = articleTagRepository.delete(articleTag.getId());

        //then
        assertThat(result).isEqualTo(1);
    }

    @DisplayName(value = "article_Id로 delete 성공")
    @Test
    void t4() {
        //given
        Long articleId = 1L;
        Long tagId = 1L;

        ArticleTag articleTag = new ArticleTag(articleId, tagId);
        articleTagRepository.persist(articleTag);

        //when
        int result = articleTagRepository.deleteByArticle(articleId);

        //then
        assertThat(result).isEqualTo(1);
    }
}