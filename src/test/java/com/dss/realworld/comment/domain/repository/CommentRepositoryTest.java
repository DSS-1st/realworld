package com.dss.realworld.comment.domain.repository;

import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.comment.domain.Comment;
import com.dss.realworld.comment.domain.dto.GetCommentDto;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

        User newUser = User.builder()
                .username("Jacob000")
                .email("jake000@jake.jake")
                .password("jakejake")
                .build();
        userRepository.addUser(newUser);

        Article newArticle = Article.builder()
                .title("How to train your dragon")
                .slug("How-to-train-your-dragon")
                .description("Ever wonder how?")
                .body("You have to believe")
                .userId(newUser.getId())
                .build();
        articleRepository.createArticle(newArticle);
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

    @Test
    void Should_Success_When_RequiredFieldsAreNotNull() {
        Comment comment = Comment.builder()
                .articleId(1L)
                .body("His name was my name too.")
                .userId(1L)
                .build();
        commentRepository.addComment(comment);
        assertThat(comment.getBody()).isEqualTo("His name was my name too.");
    }

    @Test
    void Should_Success_When_FindCommentById() {
        Comment comment = Comment.builder()
                .articleId(1L)
                .body("hello")
                .userId(1L)
                .build();
        commentRepository.addComment(comment);
        GetCommentDto getCommentDto = commentRepository.getCommentById(comment.getId());
        assertThat(getCommentDto.getBody()).isEqualTo("hello");
    }
}