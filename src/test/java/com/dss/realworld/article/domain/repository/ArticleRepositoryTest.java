package com.dss.realworld.article.domain.repository;

import com.dss.realworld.article.api.dto.UpdateArticleRequestDto;
import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.Slug;
import com.dss.realworld.error.exception.ArticleNotFoundException;
import com.dss.realworld.user.domain.FollowRelation;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.FollowRelationRepository;
import com.dss.realworld.user.domain.repository.UserRepository;
import com.dss.realworld.util.ArticleFixtures;
import com.dss.realworld.util.UserFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

//@ActiveProfiles(value = "test")
@Sql(value = {"classpath:db/teardown.sql", "classpath:db/dataSetup.sql"})
@SpringBootTest
public class ArticleRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private FollowRelationRepository followRelationRepository;

    @DisplayName(value = "필수 입력값이 NotNull이면 Article 생성 성공")
    @Test
    void t1() {
        Article newArticle = ArticleFixtures.of(1L);
        articleRepository.persist(newArticle);

        assertThat(newArticle.getId()).isNotNull();
    }

    @DisplayName(value = "Article 생성 후 DB에 부여된 PK로 조회 시 성공")
    @Test
    void t2() {
        Article newArticle = ArticleFixtures.of(1L);
        articleRepository.persist(newArticle);

        Article foundArticle = articleRepository.findById(newArticle.getId()).orElseThrow(ArticleNotFoundException::new);

        assertThat(foundArticle.getTitle()).isEqualTo(newArticle.getTitle());
    }

    @DisplayName(value = "Article 생성 후 slug로 조회 시 성공")
    @Test
    void t3() {
        String title = "How to train your dragon";
        String slug = "How-to-train-your-dragon-1";
        Long userId = 1L;

        Article newArticle = ArticleFixtures.of(title, slug, userId);
        articleRepository.persist(newArticle);

        Optional<Article> foundArticle = articleRepository.findBySlug(newArticle.getSlug());

        assertThat(newArticle.getSlug()).isEqualTo(foundArticle.get().getSlug());
    }

    @DisplayName(value = "Article Id가 유효하면 삭제 성공")
    @Test
    void t4() {
        Article newArticle = ArticleFixtures.of(1L);
        articleRepository.persist(newArticle);

        int deletedCount = articleRepository.delete(newArticle.getId());

        assertThat(deletedCount).isEqualTo(1);
    }

    @DisplayName(value = "유효하지 않은 Id로 삭제 요청 시 삭제된 Article 0개")
    @Test
    void t5() {
        Article newArticle = ArticleFixtures.of(1L);
        articleRepository.persist(newArticle);

        int deletedCount = articleRepository.delete(99L);

        assertThat(deletedCount).isEqualTo(0);
    }

    @DisplayName(value = "Article title 수정 시 slug도 변경 성공")
    @Test
    void t6() {
        Article newArticle = ArticleFixtures.of(1L);
        articleRepository.persist(newArticle);

        String newTitle = "updated title";
        String newDescription = "";
        String newBody = "";
        String newSlug = Slug.of(newTitle, newArticle.getId()).getValue();

        UpdateArticleRequestDto updateArticleRequestDto = new UpdateArticleRequestDto(newTitle, newDescription, newBody);
        articleRepository.update(newArticle.updateArticle(updateArticleRequestDto));

        Article updatedArticle = articleRepository.findById(newArticle.getId()).get();
        assertThat(updatedArticle.getTitle()).isEqualTo(newTitle);
        assertThat(updatedArticle.getSlug()).isEqualTo(newSlug);
    }

    @DisplayName(value = "팔로우한 사용자 2명의 게시글을 조회하기 성공")
    @Test
    void t7() {
        //given
        User loginUser = UserFixtures.create("newUser03", "user03pwd", "user03@realworld.com");
        userRepository.persist(loginUser);
        int midRange = 25;
        int endRange = 30;
        saveSample(loginUser.getId(), midRange, endRange);

        //when
        int limit = 20;
        int offset = 0;
        List<Article> articleFeed = articleRepository.findByFollower(loginUser.getId(), limit, offset);
        System.out.println("articleFeed = " + articleFeed);

        //then
        assertThat(articleFeed.get(0).getUserId()).isEqualTo(2);
        assertThat(articleFeed.get(5).getUserId()).isEqualTo(1);
        assertThat(articleFeed.get(0).getId()).isEqualTo(endRange);
        assertThat(articleFeed.get(limit - 1).getId()).isEqualTo(endRange - limit + 1);
        assertThat(articleFeed.size()).isEqualTo(limit); //테이블 생성 시 기본 추가되는 Article 1개 포함
    }

    private void saveSample(Long loginId, int midRange, int endRange) {
        Long followedUser1 = 1L;
        Long followedUser2 = 2L;
        followRelationRepository.persist(new FollowRelation(1L, loginId));
        followRelationRepository.persist(new FollowRelation(2L, loginId));

        for (int i = 1; i < midRange; i++) {
            Article article = ArticleFixtures.of("test sample" + i, followedUser1);
            articleRepository.persist(article);
        }

        for (int i = midRange; i < endRange; i++) {
            Article article = ArticleFixtures.of("test sample" + i, followedUser2);
            articleRepository.persist(article);
        }
    }

    @DisplayName(value = "전체 게시글 수 조회 성공")
    @Test
    void t8() {
        //given
        for (int i = 1; i < 10; i++) {
            Article article = ArticleFixtures.of("test sample" + i, 1L);
            articleRepository.persist(article);
        }

        //when
        long totalCount = articleRepository.countAll();

        //then
        assertThat(totalCount).isEqualTo(10);
    }
}