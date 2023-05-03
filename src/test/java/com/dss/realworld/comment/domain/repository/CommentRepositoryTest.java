package com.dss.realworld.comment.domain.repository;

import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.comment.domain.Comment;
import com.dss.realworld.comment.domain.dto.GetCommentDto;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import com.dss.realworld.util.ArticleFixtures;
import com.dss.realworld.util.CommentFixtures;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.dss.realworld.util.UserFixtures;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
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
        userRepository.add(newUser);

        Article newArticle = ArticleFixtures.create(newUser.getId());
        articleRepository.create(newArticle);
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

        commentRepository.deleteAll();
        commentRepository.resetAutoIncrement();
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
        GetCommentDto getCommentDto = commentRepository.getById(comment.getId());
        assertThat(getCommentDto.getBody()).isEqualTo("His name was my name too.");
    }
}