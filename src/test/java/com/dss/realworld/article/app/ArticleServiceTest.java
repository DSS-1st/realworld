package com.dss.realworld.article.app;

import com.dss.realworld.article.api.dto.ArticleListResponseDto;
import com.dss.realworld.article.api.dto.ArticleResponseDto;
import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.ArticleTag;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.article.domain.repository.ArticleTagRepository;
import com.dss.realworld.article.domain.repository.ArticleUsersRepository;
import com.dss.realworld.comment.domain.Comment;
import com.dss.realworld.comment.domain.repository.CommentRepository;
import com.dss.realworld.error.exception.ArticleNotFoundException;
import com.dss.realworld.error.exception.CustomApiException;
import com.dss.realworld.user.domain.Following;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.FollowingRepository;
import com.dss.realworld.user.domain.repository.UserRepository;
import com.dss.realworld.util.ArticleFixtures;
import com.dss.realworld.util.UserFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles(value = "test")
@Sql(value = {"classpath:db/teardown.sql", "classpath:db/dataSetup.sql"})
@SpringBootTest
public class ArticleServiceTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleTagRepository articleTagRepository;

    @Autowired
    private ArticleUsersRepository articleUsersRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowingRepository followingRepository;

    @Autowired
    private ArticleService articleService;

    @DisplayName(value = "필수 입력값, 로그인 ID가 유효하면 Article 작성 성공")
    @Test
    void t1() {
        //given
        Long userId = 1L;
        CreateArticleRequestDto createArticleRequestDto = createArticleDto();

        //when
        ArticleResponseDto articleResponseDto = articleService.save(createArticleRequestDto, userId);

        //then
        assertThat(articleResponseDto.getTitle()).isEqualTo(createArticleRequestDto.getTitle());
    }

    @DisplayName(value = "유효한 slug로 게시글 조회 시 성공")
    @Test
    void t2() {
        //given
        Long userId = 1L;
        CreateArticleRequestDto createArticleRequestDto = createArticleDto();
        ArticleResponseDto savedArticle = articleService.save(createArticleRequestDto, userId);
        assertThat(savedArticle.getTitle()).isEqualTo(createArticleRequestDto.getTitle());

        //when
        ArticleResponseDto foundArticle = articleService.findBySlug(savedArticle.getSlug(), userId);

        //then
        Assertions.assertThat(foundArticle.getSlug()).isEqualTo(savedArticle.getSlug());
    }

    @DisplayName(value = "게시글 작성자가 아닐 때 삭제 시도 시 예외 발생")
    @Test
    void t3() {
        //given
        String savedSlug = "new-title-1";

        //when
        Long wrongAuthorId = 10L;

        //then
        assertThatThrownBy(() -> articleService.delete(savedSlug, wrongAuthorId))
                .hasMessageContaining("작성자가 일치하지 않습니다.");
    }

    @DisplayName(value = "존재하지 않는 게시글을 삭제 시도 시 예외 발생")
    @Test
    void t4() {
        //given
        Long validUserId = 1L;

        //when
        String wrongArticleSlug = "wrongArticleSlug";

        //then
        assertThatThrownBy(() -> articleService.delete(wrongArticleSlug, validUserId))
                .isInstanceOf(ArticleNotFoundException.class);
    }

    private CreateArticleRequestDto createArticleDto() {
        return ArticleFixtures.createRequestDto();
    }

    @DisplayName(value = "slug와 작성자가 일치하면 Article 삭제 성공")
    @Test
    void t5() {
        //given
        Long validUserId = 1L;
        String savedSlug = "new-title-1";

        //when
        articleService.delete(savedSlug, validUserId);

        //then
        Assertions.assertThatThrownBy(() -> articleRepository.findBySlug(savedSlug).orElseThrow(ArticleNotFoundException::new)).isInstanceOf(ArticleNotFoundException.class);
    }

    @DisplayName(value = "Article 삭제 시 이를 참조하는 article_tag, comments 테이블 레코드도 삭제 성공")
    @Test
    void t6() {
        //given
        Long articleId = 1L;
        Long commentId = 1L;
        Long articleTagId = 1L;

        Comment comment = commentRepository.findById(commentId).get();
        ArticleTag articleTag = articleTagRepository.findById(articleTagId).get();
        Article article = articleRepository.findById(articleId).get();

        String slug = "new-title-1";
        Long userId = 1L;

        //when
        articleService.delete(slug, userId);

        //then
        assertThat(commentRepository.findById(comment.getId())).isEmpty();
        assertThat(articleTagRepository.findById(articleTag.getId())).isEmpty();
        assertThat(articleRepository.findById(article.getId())).isEmpty();
    }

    @DisplayName(value = "유효한 slug, favoritedId 존재 시 좋아요 성공")
    @Test
    void t7() {
        //given
        String slug = "new-title-1";
        Long favoritedId = 1L;

        //when
        ArticleResponseDto articleResponseDto = articleService.favorite(slug, favoritedId);

        //then
        assertThat(articleResponseDto.getFavoritesCount()).isEqualTo(1);
        assertThat(articleResponseDto.isFavorited()).isTrue();
    }

    @DisplayName(value = "중복으로 좋아요 시도 시 예외 발생")
    @Test
    void t8() {
        //given
        String slug = "new-title-1";
        Long favoritedId = 1L;

        //when
        articleService.favorite(slug, favoritedId);

        //then
        assertThatThrownBy(() -> articleService.favorite(slug, favoritedId)).isInstanceOf(CustomApiException.class).hasMessageContaining("이미 좋아요한 글입니다.");
    }

    @DisplayName(value = "articleId, favoritedId가 유효하면 좋아요 취소 성공")
    @Test
    void t9() {
        //given
        String slug = "new-title-1";
        Long favoritedId = 1L;
        ArticleResponseDto favoriteArticle = articleService.favorite(slug, favoritedId);
        assertThat(favoriteArticle.getFavoritesCount()).isEqualTo(1);
        assertThat(favoriteArticle.isFavorited()).isTrue();

        //when
        ArticleResponseDto unfavoriteArticle = articleService.unfavorite(slug, favoritedId);

        //then
        assertThat(unfavoriteArticle.isFavorited()).isFalse();
        assertThat(unfavoriteArticle.getFavoritesCount()).isEqualTo(0);
    }

    @DisplayName(value = "Article 삭제 시 이를 참조하는 article_users, article_tag, comments 테이블 레코드도 삭제 성공")
    @Test
    void t10() {
        //given
        Long articleId = 1L;
        Long commentId = 1L;
        Long articleTagId = 1L;

        Comment comment = commentRepository.findById(commentId).get();
        ArticleTag articleTag = articleTagRepository.findById(articleTagId).get();
        Article article = articleRepository.findById(articleId).get();

        String slug = "new-title-1";
        Long userId = 1L;
        articleService.favorite(slug, userId);

        //when
        articleService.delete(slug, userId);

        //then
        assertThat(commentRepository.findById(comment.getId())).isEmpty();
        assertThat(articleTagRepository.findById(articleTag.getId())).isEmpty();
        assertThat(articleUsersRepository.findCountByArticleId(article.getId())).isEqualTo(0);
        assertThat(articleRepository.findById(article.getId())).isEmpty();
    }

    @DisplayName(value = "loginId와 페이징 조건이 유효하면 Article Feed 불러오기 성공")
    @Test
    void t11() {
        //given
        User user = UserFixtures.create("user100", "user100pwd", "user100@realworld.com");
        userRepository.persist(user);

        Long loginId = user.getId();
        followingRepository.persist(new Following(1L, loginId));

        //when
        ArticleListResponseDto articles = articleService.feed(loginId, 20, 0);

        //then
        assertThat(articles.getArticles().get(0).getAuthor().isFollowing()).isTrue();
        assertThat(articles.getArticlesCount()).isEqualTo(1);
    }

    @DisplayName(value = "팔로우한 사용자가 없으면 빈 dto 반환")
    @Test
    void t12() {
        //given
        User user = UserFixtures.create("user100", "user100pwd", "user100@realworld.com");
        userRepository.persist(user);

        Long loginId = user.getId();

        //when
        ArticleListResponseDto articles = articleService.feed(loginId, 20, 0);

        //then
        assertThat(articles.getArticles()).isEmpty();
        assertThat(articles.getArticlesCount()).isEqualTo(0);
    }

    @DisplayName(value = "특정 Author가 작성한 게시글 조회 시 팔로잉 여부 출력 성공")
    @Test
    void t13() {
        //given
        User loginUser = UserFixtures.create("newUser03", "user03pwd", "user03@realworld.com");
        userRepository.persist(loginUser);
        Long midRangeAuthorId = 1L;
        Long endRangeAuthorId = 2L;
        SaveFollowingSample(midRangeAuthorId, endRangeAuthorId, loginUser);

        int midRange = 11;
        int endRange = 30;
        saveArticleSample(midRange, endRange, midRangeAuthorId, endRangeAuthorId); //AuthorId가 2L인 Article 20개 생성(11~30)

        String author = userRepository.findById(endRangeAuthorId).get().getUsername(); //Kate

        //when
        int limit = 20;
        int offset = 20;
        ArticleListResponseDto articles = articleService.list(null, author, null, loginUser.getId(), limit, offset);

        //then
        assertThat(articles.getArticles().get(0).getAuthor().getUsername()).isEqualTo(author);
        assertThat(articles.getArticles().get(1).getAuthor().isFollowing()).isTrue();
        assertThat(articles.getArticlesCount()).isEqualTo(2); //@Sql에 선언된 샘플 데이터 2개 포함
    }

    private void SaveFollowingSample(final Long targetId1, final Long targetId2, final User loginUser) {
        followingRepository.persist(new Following(targetId1, loginUser.getId()));
        followingRepository.persist(new Following(targetId2, loginUser.getId()));
    }

    private void saveArticleSample(int midRange, int endRange, final Long midRangeAuthorId, final Long endRangeAuthorId) {
        for (int i = 1; i < midRange; i++) {
            Article article = ArticleFixtures.of("test sample" + i, midRangeAuthorId);
            articleRepository.persist(article);
        }

        for (int i = midRange; i <= endRange; i++) {
            Article article = ArticleFixtures.of("test sample" + i, endRangeAuthorId);
            articleRepository.persist(article);
        }
    }
}