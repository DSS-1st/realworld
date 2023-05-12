package com.dss.realworld.article.app;

import com.dss.realworld.article.api.dto.ArticleResponseDto;
import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.error.exception.ArticleNotFoundException;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import com.dss.realworld.util.ArticleFixtures;
import com.dss.realworld.util.UserFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class ArticleServiceTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        clearTable();
    }

    @AfterEach
    void teatDown() {
        clearTable();
    }

    private void clearTable() {
        userRepository.deleteAll();
        userRepository.resetAutoIncrement();

        articleRepository.deleteAll();
        articleRepository.resetAutoIncrement();
    }

    @DisplayName(value = "필수 입력값, 로그인 ID가 유효하면 Article 작성 성공")
    @Test
    void t1() {
        //given
        CreateArticleRequestDto createArticleRequestDto = createArticleDto();

        //when
        ArticleResponseDto articleResponseDto = articleService.save(createArticleRequestDto, getSampleUserId());

        //then
        assertThat(articleResponseDto.getTitle()).isEqualTo(createArticleRequestDto.getTitle());
    }

    @DisplayName(value = "유효한 slug로 게시글 조회 시 성공")
    @Test
    void t2() {
        //given
        CreateArticleRequestDto createArticleRequestDto = createArticleDto();
        ArticleResponseDto savedArticle = articleService.save(createArticleRequestDto, getSampleUserId());
        assertThat(savedArticle.getTitle()).isEqualTo(createArticleRequestDto.getTitle());

        //when
        ArticleResponseDto foundArticle = articleService.findBySlug(savedArticle.getSlug());

        //then
        Assertions.assertThat(foundArticle.getSlug()).isEqualTo(savedArticle.getSlug());
    }

    private Long getSampleUserId() {
        User user = UserFixtures.create();
        userRepository.persist(user);

        return user.getId();
    }

    @DisplayName(value = "게시글 작성자가 아닐 때 삭제 시도 시 예외 발생")
    @Test
    void t3() {
        //given
        Long validUserId = 1L;
        Article newArticle = saveArticle(validUserId);
        Article savedArticle = articleRepository.findById(newArticle.getId()).orElseThrow(ArticleNotFoundException::new);
        assertThat(savedArticle.getId()).isEqualTo(validUserId);

        //when
        String savedSlug = savedArticle.getSlug();
        Long wrongAuthorId = 10L;

        //then
        assertThatThrownBy(() -> articleService.delete(savedSlug, wrongAuthorId))
                .hasMessageContaining("작성자가 일치하지 않습니다.");
    }

    private Article saveArticle(Long userId) {
        Article newArticle = ArticleFixtures.of(userId);
        articleRepository.persist(newArticle);

        return newArticle;
    }

    @DisplayName(value = "존재하지 않는 게시글을 삭제 시도 시 예외 발생")
    @Test
    void t4() {
        //given
        Long validUserId = 1L;
        Article newArticle = saveArticle(validUserId);
        Article savedArticle = articleRepository.findById(newArticle.getId()).orElseThrow(ArticleNotFoundException::new);
        assertThat(savedArticle.getId()).isEqualTo(validUserId);

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
        Article newArticle = saveArticle(validUserId);
        Article savedArticle = articleRepository.findById(newArticle.getId()).orElseThrow(ArticleNotFoundException::new);
        assertThat(savedArticle.getUserId()).isEqualTo(validUserId);

        //when
        articleService.delete(savedArticle.getSlug(), validUserId);

        //then
        Assertions.assertThatThrownBy(()->articleRepository.findBySlug(savedArticle.getSlug()).orElseThrow(ArticleNotFoundException::new)).isInstanceOf(ArticleNotFoundException.class);
    }

    // Article Service Test는 Article Controller Test(t4)를 통해 수정 전후 DTO 검증
}