package com.dss.realworld.article.app;

import com.dss.realworld.article.api.dto.ArticleResponseDto;
import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.ArticleTag;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.article.domain.repository.ArticleTagRepository;
import com.dss.realworld.comment.domain.Comment;
import com.dss.realworld.comment.domain.repository.CommentRepository;
import com.dss.realworld.error.exception.ArticleNotFoundException;
import com.dss.realworld.util.ArticleFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

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
        ArticleResponseDto foundArticle = articleService.findBySlug(savedArticle.getSlug());

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
        Optional<Comment> comment = commentRepository.findById(1L);
        ArticleTag articleTag = articleTagRepository.findById(1L);
        Article article = articleRepository.findById(1L).get();

        String slug = "new-title-1";
        Long userId = 1L;

        //when
        articleService.delete(slug, 1L);

        //then
        assertThat(commentRepository.findById(1L)).isEmpty();
        assertThat(articleTagRepository.findById(1L)).isNull();
        assertThat(articleRepository.findById(1L).orElse(null)).isNull();
    }
}