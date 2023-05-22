package com.dss.realworld.article.domain.repository;

import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.ArticleTag;
import com.dss.realworld.tag.domain.Tag;
import com.dss.realworld.tag.domain.repository.TagRepository;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import com.dss.realworld.util.ArticleFixtures;
import com.dss.realworld.util.UserFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(value = "test")
@Sql(value = {"classpath:db/ArticleTeardown.sql","classpath:db/UserTeardown.sql","classpath:db/TagTeardown.sql","classpath:db/ArticleTagTeardown.sql"})
@SpringBootTest
@Transactional
public class ArticleTagRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ArticleTagRepository articleTagRepository;

    @DisplayName(value = "Article, Tag 존재 시 insert 성공")
    @Test
    void t1() {
        //given
        User user = UserFixtures.create();
        userRepository.persist(user);

        Article article = ArticleFixtures.of(user.getId());
        articleRepository.persist(article);

        Tag tag = new Tag("qwerty");
        tagRepository.persist(tag);

        ArticleTag articleTag = new ArticleTag(article.getId(), tag.getId());

        //when
        articleTagRepository.persist(articleTag);

        //then
        assertThat(articleTag.getId()).isNotNull();
    }

    @DisplayName(value = "articleTagId로 조회 성공")
    @Test
    void t2() {
        //given
        User user = UserFixtures.create();
        userRepository.persist(user);

        Article article = ArticleFixtures.of(user.getId());
        articleRepository.persist(article);

        Tag tag = new Tag("qwerty");
        tagRepository.persist(tag);

        ArticleTag articleTag = new ArticleTag(article.getId(), tag.getId());
        articleTagRepository.persist(articleTag);

        //when
        ArticleTag foundArticleTag = articleTagRepository.findById(articleTag.getId());

        //then
        assertThat(foundArticleTag.getId()).isEqualTo(articleTag.getId());
    }

    @DisplayName(value = "articleTagId로 delete 성공")
    @Test
    void t3() {
        //given
        User user = UserFixtures.create();
        userRepository.persist(user);

        Article article = ArticleFixtures.of(user.getId());
        articleRepository.persist(article);

        Tag tag = new Tag("qwerty");
        tagRepository.persist(tag);

        ArticleTag articleTag = new ArticleTag(article.getId(), tag.getId());
        articleTagRepository.persist(articleTag);

        //when
        int result = articleTagRepository.delete(tag.getId());

        //then
        assertThat(result).isEqualTo(1);
    }

    @DisplayName(value = "articleId로 delete 성공")
    @Test
    void t4() {
        //given
        User user = UserFixtures.create();
        userRepository.persist(user);

        Article article = ArticleFixtures.of(user.getId());
        articleRepository.persist(article);

        Tag tag = new Tag("qwerty");
        tagRepository.persist(tag);

        ArticleTag articleTag = new ArticleTag(article.getId(), tag.getId());
        articleTagRepository.persist(articleTag);

        //when
        int result = articleTagRepository.deleteByArticle(article.getId());

        //then
        assertThat(result).isEqualTo(1);
    }
}