package com.dss.realworld.comment.app;

import com.dss.realworld.comment.api.dto.AddCommentRequestDto;
import com.dss.realworld.comment.api.dto.AddCommentResponseDto;
import com.dss.realworld.comment.api.dto.CommentDto;
import com.dss.realworld.comment.domain.Comment;
import com.dss.realworld.comment.domain.repository.CommentRepository;
import com.dss.realworld.util.CommentFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(value = "test")
@SpringBootTest
@Sql(value = {"classpath:db/teardown.sql", "classpath:db/dataSetup.sql"})
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName(value = "requestDto가 유효하면 댓글 작성 성공")
    @Test
    void t1() {
        //given
        String comment = "His name was my name too.";
        AddCommentRequestDto addCommentRequestDto = new AddCommentRequestDto(comment);
        Long loginId = 1L;
        String slug = "new-title-1";

        //when
        AddCommentResponseDto saveComment = commentService.add(addCommentRequestDto, loginId, slug);

        //then
        Assertions.assertThat(saveComment.getBody()).isEqualTo(comment);
    }

    @DisplayName(value = "파라미터가 유효하면 댓글 삭제 성공")
    @Test
    void t2() {
        //given
        String slug = "new-title-1";
        Long commentId = 1L;
        Long userId = 1L;

        //when
        final int result = commentService.delete(slug, commentId, userId);

        //then
        assertThat(result).isEqualTo(1);
    }

    @DisplayName(value = "slug가 유효하면 여러 comment 가져오기 성공")
    @Test
    void t3() {
        //given
        Comment newComment1 = CommentFixtures.create();
        Comment newComment2 = CommentFixtures.create();
        commentRepository.persist(newComment1);
        commentRepository.persist(newComment2);
        String slug = "new-title-1";
        Long loginId = 1L;

        //when
        List<CommentDto> commentList = commentService.getAll(slug, loginId);

        //then
        assertThat(commentList.size()).isEqualTo(3);
    }
}