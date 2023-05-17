package com.dss.realworld.comment.domain.repository;

import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.comment.domain.Comment;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import com.dss.realworld.util.ArticleFixtures;
import com.dss.realworld.util.CommentFixtures;
import com.dss.realworld.util.UserFixtures;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Sql(value = {"classpath:db/CommentTearDown.sql"})
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        clearTable();

        User newUser = UserFixtures.create();
        userRepository.persist(newUser);

        Article newArticle = ArticleFixtures.of(newUser.getId());
        articleRepository.persist(newArticle);
    }

    private void clearTable() {
        userRepository.deleteAll();
        userRepository.resetAutoIncrement();

        articleRepository.deleteAll();
        articleRepository.resetAutoIncrement();
    }

    @DisplayName(value = "articeId,body,userId가 NotNull이면 댓글 작성 성공")
    @Test
    void t1() {
        Comment comment = CommentFixtures.create();
        commentRepository.add(comment);
        assertThat(comment.getBody()).isEqualTo("His name was my name too.");
    }

    @DisplayName(value = "commentId가 존재하면 comment 반환 성공")
    @Test
    void t2() {
        Comment comment = CommentFixtures.create();
        commentRepository.add(comment);
        Comment getCommentDto = commentRepository.getById(comment.getId());
        assertThat(getCommentDto.getBody()).isEqualTo("His name was my name too.");
    }

    @DisplayName(value = "commentId가 NotNull이면 댓글 삭제 성공")
    @Test
    void t3() {
        Long articleId = 1L;
        Long userId = 1L;
        Comment comment = Comment.builder()
                .articleId(articleId)
                .body("His name was my name too.")
                .userId(userId)
                .build();
        commentRepository.add(comment);
        final int result = commentRepository.deleteComment(comment.getId(),articleId,userId);
        assertThat(result).isEqualTo(1);
    }

    @DisplayName(value = "articleId가 NotNull이면 댓글 가져오기 성공")
    @Test
    void t4() {
        Comment comment1 = CommentFixtures.create();
        Comment comment2 = CommentFixtures.create();

        commentRepository.add(comment1);
        commentRepository.add(comment2);

        List<Comment> comments = commentRepository.getAll(1L);

        assertThat(comments.size()).isEqualTo(2);
    }
}