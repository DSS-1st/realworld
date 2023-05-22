package com.dss.realworld.comment.api;

import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.comment.api.dto.AddCommentRequestDto;
import com.dss.realworld.comment.app.CommentService;
import com.dss.realworld.comment.domain.Comment;
import com.dss.realworld.comment.domain.repository.CommentRepository;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import com.dss.realworld.util.ArticleFixtures;
import com.dss.realworld.util.CommentFixtures;
import com.dss.realworld.util.UserFixtures;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"classpath:db/CommentTearDown.sql", "classpath:db/UserTearDown.sql", "classpath:db/ArticleTearDown.sql"})
class CommentControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        User newUser = UserFixtures.create();
        userRepository.persist(newUser);

        Article newArticle = ArticleFixtures.of(newUser.getId());
        articleRepository.persist(newArticle);
    }

    @DisplayName(value = "AddCommentRequestDto와 Slug가 NotNull이 아니면 댓글 작성 성공")
    @Test
    void t1() throws Exception {
        //given
        String slug = "How-to-train-your-dragon-1";
        AddCommentRequestDto addCommentRequestDto = createAddCommentRequestDto();

        //when
        String jsonString = objectMapper.writeValueAsString(addCommentRequestDto);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/articles/{slug}/comments", slug)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString);

        //then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..id").exists())
                .andExpect(jsonPath("$..createdAt").exists())
                .andExpect(jsonPath("$..updatedAt").exists())
                .andExpect(jsonPath("$..body").value("His name was my name too."));
    }

    @DisplayName(value = "AddCommentRequestDto와 Slug가 NotNull이 아니면 댓글 삭제 성공")
    @Test
    void t2() throws Exception {
        //given
        Long commentId = 1L;
        Long logonUserId = 1L;
        String slug = "How-to-train-your-dragon-1";
        AddCommentRequestDto addCommentRequestDto = createAddCommentRequestDto();
        commentService.add(addCommentRequestDto, logonUserId, slug);

        //when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .delete("/api/articles/{slug}/comments/{id}", slug, commentId)
                .contentType(MediaType.APPLICATION_JSON);

        //then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @DisplayName(value = "Slug 값 유효하면 댓글 리스트 가져오기 성공")
    @Test
    void t3() throws Exception {
        //given
        Comment comment1 = CommentFixtures.create();
        Comment comment2 = CommentFixtures.create();

        commentRepository.add(comment1);
        commentRepository.add(comment2);

        String slug = "How-to-train-your-dragon-1";

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/articles/{slug}/comments", slug)
                .contentType(MediaType.APPLICATION_JSON);

        //when
        ResultActions resultActions = mockMvc.perform(mockRequest);
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);

        //then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.comments.size()").value(2))
            .andExpect(jsonPath("$.comments[0].id").value(1))
            .andExpect(jsonPath("$.comments[0].id").isNumber())
            .andExpect(jsonPath("$.comments[0].createdAt").exists())
            .andExpect(jsonPath("$.comments[0].updatedAt").exists())
            .andExpect(jsonPath("$.comments[0].body").exists())
            .andExpect(jsonPath("$.comments[0].author.username").exists())
            .andExpect(jsonPath("$.comments[0].author.following").value(false))
            .andExpect(jsonPath("$.comments[0].author.following").value(false));
    }

    private AddCommentRequestDto createAddCommentRequestDto() {
        String body = "His name was my name too.";

        return new AddCommentRequestDto(body);
    }
}