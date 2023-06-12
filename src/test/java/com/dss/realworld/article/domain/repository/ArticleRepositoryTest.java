package com.dss.realworld.article.domain.repository;

import com.dss.realworld.article.api.dto.UpdateArticleRequestDto;
import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.Slug;
import com.dss.realworld.common.error.exception.ArticleNotFoundException;
import com.dss.realworld.user.domain.Following;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.FollowingRepository;
import com.dss.realworld.user.domain.repository.UserRepository;
import com.dss.realworld.util.ArticleFixtures;
import com.dss.realworld.util.UserFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(value = "test")
@Sql(value = {"classpath:db/teardown.sql", "classpath:db/dataSetup.sql"})
@SpringBootTest
public class ArticleRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private FollowingRepository followingRepository;

    @Autowired
    private ArticleTagRepository articleTagRepository;

    @Autowired
    private ArticleUsersRepository articleUsersRepository;

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

    @DisplayName(value = "팔로우한 사용자 2명의 게시글 목록을 조회하기 성공")
    @Test
    void t7() {
        //given
        User loginUser = UserFixtures.create("newUser03", "user03pwd", "user03@realworld.com");
        userRepository.persist(loginUser);
        Long midRangeAuthorId = 1L;
        Long endRangeAuthorId = 2L;
        saveFollowingSample(midRangeAuthorId, endRangeAuthorId, loginUser);

        int midRange = 25;
        int endRange = 30;
        saveArticleSample(midRange, endRange, midRangeAuthorId, endRangeAuthorId);

        //when
        int limit = 20;
        int offset = 0;
        List<Article> articleFeed = articleRepository.findArticleByFollower(loginUser.getId(), limit, offset);
        System.out.println("articleFeed = " + articleFeed);

        //then
        assertThat(articleFeed.get(0).getUserId()).isEqualTo(2); //saveSample()에서 저장한 글 작성자 2번
        assertThat(articleFeed.get(6).getUserId()).isEqualTo(1); //saveSample()에서 저장한 글 작성자 1번
        assertThat(articleFeed.get(0).getId()).isEqualTo(endRange + 3); //테이블 생성 시 기본 추가되는 Article 3개 포함
        assertThat(articleFeed.size()).isEqualTo(limit);
    }

    private void saveFollowingSample(final Long targetId1, final Long targetId2, final User loginUser) {
        followingRepository.persist(new Following(targetId1, loginUser.getId()));
        followingRepository.persist(new Following(targetId2, loginUser.getId()));
    }

    private void saveArticleSample(int midRange, int endRange, final Long midRangeAuthorId, final Long endRangeAuthorId) {
        String title = "test sample title ";

        for (int i = 1; i < midRange; i++) {
            Long articleId = articleRepository.findMaxId().orElse(0L) + 1;
            Article article = ArticleFixtures.of(title + i, Slug.of(title, articleId).getValue(), midRangeAuthorId);
            articleRepository.persist(article);
        }
        for (int i = midRange; i <= endRange; i++) {
            Long articleId = articleRepository.findMaxId().orElse(0L) + 1;
            Article article = ArticleFixtures.of(title + i, Slug.of(title, articleId).getValue(), endRangeAuthorId);
            articleRepository.persist(article);
        }
    }

    @DisplayName(value = "특정 tag가 등록된 게시글 조회 성공")
    @Test
    void t8() {
        //given
        String tag = "dvorak";
        Long articldId = 1L;
        List<String> tagsByArticleId = articleTagRepository.findTagsByArticleId(articldId);

        //when
        List<Article> articles = articleRepository.list(tag, null, null, 20, 0);

        //then
        assertThat(articles.size()).isEqualTo(1);
        assertThat(articles.get(0).getId()).isEqualTo(1);
        assertThat(tagsByArticleId.contains(tag)).isTrue();
    }

    @DisplayName(value = "특정 author가 등록한 게시글 조회 성공")
    @Test
    void t9() {
        //given
        String author = "Kate";
        Long authorId = userRepository.findByUsername(author).get().getId();

        //when
        List<Article> articles = articleRepository.list(null, author, null, 20, 0);

        //then
        assertThat(articles.get(0).getUserId()).isEqualTo(authorId);
    }

    @DisplayName(value = "특정 user가 좋아한 게시글 조회 성공")
    @Test
    void t10() {
        //given
        String favoritedBy = "Jacob";
        Long favoritedId = userRepository.findByUsername(favoritedBy).get().getId();
        Long articleId = 3L;

        //when
        int isFavorite = articleUsersRepository.isFavorite(articleId, favoritedId);
        assertThat(isFavorite).isEqualTo(1);
        List<Article> articles = articleRepository.list(null, null, favoritedBy, 20, 0);

        //then
        assertThat(articles.get(0).getId()).isEqualTo(articleId);
    }
}