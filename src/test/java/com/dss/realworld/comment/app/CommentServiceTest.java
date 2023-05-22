package com.dss.realworld.comment.app;

import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.comment.api.dto.AddCommentRequestDto;
import com.dss.realworld.comment.api.dto.AddCommentResponseDto;
import com.dss.realworld.comment.domain.Comment;
import com.dss.realworld.comment.domain.repository.CommentRepository;
import com.dss.realworld.user.domain.repository.UserRepository;
import com.dss.realworld.util.CommentFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(value = "test")
@SpringBootTest
@Sql(value = {"classpath:db/teardown.sql", "classpath:db/dataSetup.sql"})
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName(value = "매개변수들이 유효하면 댓글 작성 성공")
    @Test
    void t1() {
        //given
        AddCommentRequestDto addCommentRequestDto = createAddCommentRequestDto();
        Long logonUserId = 1L;
        String slug = "new-title-1";

        //when
        AddCommentResponseDto saveComment = commentService.add(addCommentRequestDto, logonUserId, slug);

        //then
        Assertions.assertThat(saveComment.getBody()).isEqualTo("His name was my name too.");
    }

    @DisplayName(value = "commentId가 유효하면 댓글 삭제 성공")
    @Test
    void t2() {
        //given
        Comment comment1 = CommentFixtures.create();
        commentRepository.add(comment1);

        Long commnetId = 1L;
        Long articleId = 1L;
        Long userId = 1L;

        //when
        final int result = commentRepository.delete(commnetId, articleId, userId);

        //then
        assertThat(result).isEqualTo(1);
    }

    @DisplayName(value = "slug 유효하면 comment 리스트 가져오기")
    @Test
    void t3() {
        Comment comment1 = CommentFixtures.create();
        Comment comment2 = CommentFixtures.create();
        commentRepository.add(comment1);
        commentRepository.add(comment2);

        String slug = "new-title-1";

        assertThat(commentService.getAll(slug).size()).isEqualTo(2);
    }

    private AddCommentRequestDto createAddCommentRequestDto() {
        String body = "His name was my name too.";

        return new AddCommentRequestDto(body);
    }
}