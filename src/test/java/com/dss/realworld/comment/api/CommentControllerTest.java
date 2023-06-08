package com.dss.realworld.comment.api;

import com.dss.realworld.comment.api.dto.AddCommentRequestDto;
import com.dss.realworld.comment.domain.Comment;
import com.dss.realworld.comment.domain.repository.CommentRepository;
import com.dss.realworld.util.CommentFixtures;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = "test")
@Sql(value = {"classpath:db/teardown.sql", "classpath:db/dataSetup.sql"})
@AutoConfigureMockMvc
@SpringBootTest
class CommentControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName(value = "AddCommentRequestDto와 Slug가 NotNull이 아니면 댓글 작성 성공")
    @WithUserDetails(value = "jake@jake.jake")
    @Test
    void t1() throws Exception {
        //given
        String slug = "new-title-1";
        AddCommentRequestDto addCommentRequestDto = createAddCommentRequestDto();

        //when
        String jsonString = objectMapper.writeValueAsString(addCommentRequestDto);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/articles/{slug}/comments", slug)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString);

        //then
        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$..id").exists())
                .andExpect(jsonPath("$..createdAt").exists())
                .andExpect(jsonPath("$..updatedAt").exists())
                .andExpect(jsonPath("$..body").value("His name was my name too."));
    }

    @DisplayName(value = "로그인하지 않으면 아니면 댓글 작성 실패")
    @Test
    void t2() throws Exception {
        //given
        String slug = "new-title-1";
        AddCommentRequestDto addCommentRequestDto = createAddCommentRequestDto();

        //when
        String jsonString = objectMapper.writeValueAsString(addCommentRequestDto);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/articles/{slug}/comments", slug)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString);

        //then
        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.statusCode").value(401))
                .andExpect(jsonPath("$.errors.body[0]").value("로그인이 필요합니다."));
    }

    @DisplayName(value = "slug, commentId가 유효하면 댓글 삭제 성공")
    @WithUserDetails(value = "jake@jake.jake")
    @Test
    void t3() throws Exception {
        //given
        String slug = "new-title-1";
        Long commentId = 1L;

        //when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .delete("/api/articles/{slug}/comments/{id}", slug, commentId);

        //then
        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @DisplayName(value = "로그인하지 않으면 댓글 삭제 실패")
    @Test
    void t4() throws Exception {
        //given
        String slug = "new-title-1";
        Long commentId = 1L;

        //when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .delete("/api/articles/{slug}/comments/{id}", slug, commentId);

        //then
        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.statusCode").value(401))
                .andExpect(jsonPath("$.errors.body[0]").value("로그인이 필요합니다."));
    }

    @DisplayName(value = "로그인 상태에서 댓글 목록 중에 팔로우한 상대의 댓글은 following 상태가 true")
    @WithUserDetails(value = "jake@jake.jake")
    @Test
    void t5() throws Exception {
        //given
        Long followingAuthorId = 2L;
        String followingAuthorName = "Kate";
        Comment newComment1 = CommentFixtures.create(followingAuthorId);
        Comment newComment2 = CommentFixtures.create(followingAuthorId);

        commentRepository.persist(newComment1);
        commentRepository.persist(newComment2);

        String slug = "new-title-1";

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/articles/{slug}/comments", slug)
                .contentType(MediaType.APPLICATION_JSON);

        //when
        ResultActions resultActions = mockMvc.perform(mockRequest);

        //then
        resultActions
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.comments.size()").value(3))
            .andExpect(jsonPath("$.comments[1].id").exists())
            .andExpect(jsonPath("$.comments[2].id").exists())
            .andExpect(jsonPath("$.comments[1].createdAt").exists())
            .andExpect(jsonPath("$.comments[1].updatedAt").exists())
            .andExpect(jsonPath("$.comments[1].body").exists())
            .andExpect(jsonPath("$.comments[1].author.username").value(followingAuthorName))
            .andExpect(jsonPath("$.comments[2].author.username").value(followingAuthorName))
            .andExpect(jsonPath("$.comments[0].author.following").value(false))
            .andExpect(jsonPath("$.comments[1].author.following").value(true))
            .andExpect(jsonPath("$.comments[2].author.following").value(true));
    }

    @DisplayName(value = "로그인 없이 댓글 목록 요청 시 following 상태 false")
    @Test
    void t6() throws Exception {
        //given
        Long AuthorId = 1L;
        String AuthorName = "Jacob";

        Comment newComment1 = CommentFixtures.create();
        Comment newComment2 = CommentFixtures.create();

        commentRepository.persist(newComment1);
        commentRepository.persist(newComment2);

        String slug = "new-title-1";

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/articles/{slug}/comments", slug)
                .contentType(MediaType.APPLICATION_JSON);

        //when
        ResultActions resultActions = mockMvc.perform(mockRequest);

        //then
        resultActions
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.comments.size()").value(3))
                .andExpect(jsonPath("$.comments[0].id").value(AuthorId))
                .andExpect(jsonPath("$.comments[0].createdAt").exists())
                .andExpect(jsonPath("$.comments[0].updatedAt").exists())
                .andExpect(jsonPath("$.comments[0].body").exists())
                .andExpect(jsonPath("$.comments[0].author.username").value(AuthorName))
                .andExpect(jsonPath("$.comments[0].author.following").value(false))
                .andExpect(jsonPath("$.comments[1].author.following").value(false))
                .andExpect(jsonPath("$.comments[2].author.following").value(false));
    }

    private AddCommentRequestDto createAddCommentRequestDto() {
        String body = "His name was my name too.";

        return new AddCommentRequestDto(body);
    }
}