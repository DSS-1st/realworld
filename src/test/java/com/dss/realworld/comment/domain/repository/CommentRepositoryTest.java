package com.dss.realworld.comment.domain.repository;

import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.comment.domain.Comment;
import com.dss.realworld.user.domain.repository.UserRepository;
import com.dss.realworld.util.CommentFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(value = {"classpath:db/teardown.sql", "classpath:db/dataSetup.sql"})
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @DisplayName(value = "articeId, body, userId가 NotNull이면 댓글 작성 성공")
    @Test
    void t1() {
        //given
        Comment comment = CommentFixtures.create();

        //when
        commentRepository.add(comment);

        //then
        assertThat(comment.getBody()).isEqualTo("His name was my name too.");
    }

    @DisplayName(value = "commentId가 존재하면 comment 반환 성공")
    @Test
    void t2() {
        //given
        Comment comment = CommentFixtures.create();
        commentRepository.add(comment);

        //when
        Comment getCommentDto = commentRepository.getById(comment.getId());

        //then
        assertThat(getCommentDto.getBody()).isEqualTo("His name was my name too.");
    }

    @DisplayName(value = "commentId가 NotNull이면 댓글 삭제 성공")
    @Test
    void t3() {
        //given
        Long articleId = 1L;
        Long userId = 1L;
        Comment comment = Comment.builder()
                .articleId(articleId)
                .body("His name was my name too.")
                .userId(userId)
                .build();
        commentRepository.add(comment);

        //when
        int result = commentRepository.delete(comment.getId(), articleId, userId);

        //then
        assertThat(result).isEqualTo(1);
    }

    @DisplayName(value = "articleId가 NotNull이면 댓글 가져오기 성공")
    @Test
    void t4() {
        //given
        Comment comment1 = CommentFixtures.create();
        Comment comment2 = CommentFixtures.create();
        commentRepository.add(comment1);
        commentRepository.add(comment2);

        //when
        List<Comment> comments = commentRepository.getAll(1L);

        //then
        assertThat(comments.size()).isEqualTo(2);
    }
}