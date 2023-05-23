package com.dss.realworld.comment.domain.repository;

import com.dss.realworld.comment.domain.Comment;
import com.dss.realworld.util.CommentFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(value = "test")
@Sql(value = {"classpath:db/teardown.sql", "classpath:db/dataSetup.sql"})
@SpringBootTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName(value = "articeId, body, userId가 NotNull이면 댓글 작성 성공")
    @Test
    void t1() {
        //given
        Comment comment = CommentFixtures.create();

        //when
        commentRepository.persist(comment);

        //then
        assertThat(comment.getBody()).isEqualTo("His name was my name too.");
    }

    @DisplayName(value = "commentId가 존재하면 comment 반환 성공")
    @Test
    void t2() {
        //given
        Comment comment = CommentFixtures.create();
        commentRepository.persist(comment);

        //when
        Comment getCommentDto = commentRepository.findById(comment.getId());

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
        commentRepository.persist(comment);

        //when
        int result = commentRepository.delete(comment.getId(), articleId, userId);

        //then
        assertThat(result).isEqualTo(1);
    }

    @DisplayName(value = "articleId가 NotNull이면 댓글 가져오기 성공")
    @Test
    void t4() {
        //given
        Comment oldComment = commentRepository.findById(1L);
        Comment newComment1 = CommentFixtures.create();
        Comment newComment2 = CommentFixtures.create();
        commentRepository.persist(newComment1);
        commentRepository.persist(newComment2);

        //when
        List<Comment> comments = commentRepository.findAll(1L);

        //then
        assertThat(comments.size()).isEqualTo(3);
    }
}