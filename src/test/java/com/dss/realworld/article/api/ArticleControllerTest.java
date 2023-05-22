package com.dss.realworld.article.api;

import com.dss.realworld.article.api.dto.ArticleResponseDto;
import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.api.dto.UpdateArticleRequestDto;
import com.dss.realworld.article.app.ArticleService;
import com.dss.realworld.article.domain.Slug;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import com.dss.realworld.util.ArticleFixtures;
import com.dss.realworld.util.UserFixtures;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ActiveProfiles(value = "test")
@Sql(value = {"classpath:db/UserTeardown.sql","classpath:db/ArticleTeardown.sql"})
@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;

    @DisplayName(value = "필수 입력값이 NotNull일 때 Article 생성 성공")
    @Test
    void t1() throws Exception {
        //given
        userRepository.persist(UserFixtures.create());

        String title = "How to train your dragon";
        String description = "Ever wonder how?";
        String body = "You have to believe";
        CreateArticleRequestDto newArticle = ArticleFixtures.createRequestDto(title, description, body);

        String jsonString = objectMapper.writeValueAsString(newArticle);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonString);

        //when
        ResultActions resultActions = mockMvc.perform(mockRequest);
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);

        //then
        resultActions
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$..title").value(title))
            .andExpect(jsonPath("$..slug").value(Slug.of(title, 0L, true).getValue()))
            .andExpect(jsonPath("$..favorited").value(false))
            .andExpect(jsonPath("$..following").value(false))
            .andExpect(jsonPath("$..username").value("Jacob000"))
            .andExpect(jsonPath("$..description").value(description))
            .andExpect(jsonPath("$..body").value(body))
            .andExpect(jsonPath("$..tagList.length()").value(0));
    }

    @DisplayName(value = "slug, userId가 유효하면 Article 삭제 성공")
    @Test
    void t2() throws Exception {
        //given
        userRepository.persist(UserFixtures.create());
        CreateArticleRequestDto articleDto = createArticleDto();
        ArticleResponseDto articleResponseDto = articleService.save(articleDto, 1L);
        assertThat(articleResponseDto.getTitle()).isEqualTo(articleDto.getTitle());

        //when
        String slug = articleResponseDto.getSlug();
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .delete("/api/articles/" + slug)
                .contentType(MediaType.TEXT_PLAIN);

        //then
        mockMvc.perform(mockRequest).andExpect(status().isOk());
    }

    private CreateArticleRequestDto createArticleDto() {
        return ArticleFixtures.createRequestDto();
    }

    @DisplayName(value = "유효한 slug 사용 시 조회 성공")
    @Test
    void t3() throws Exception {
        //given
        String slug = "How-to-train-your-dragon-1";
        articleRepository.persist(ArticleFixtures.of(slug, getSampleUserId()));

        //when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/articles/" + slug);

        //then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..slug").value(slug));
    }

    private Long getSampleUserId() {
        User user = UserFixtures.create("kate", "katepwd", "kate@kate.kate");
        userRepository.persist(user);

        return user.getId();
    }

    @DisplayName(value = "Article 수정 시 slug도 수정 성공")
    @Test
    void t4() throws Exception {
        //given
        String title = "How to train your dragon";
        String slug = Slug.of(title, 0L, true).getValue();
        articleRepository.persist(ArticleFixtures.of(title, slug, getSampleUserId()));

        String newTitle = "new title";
        String newDescription = "new description";
        String newBody = "new body";
        UpdateArticleRequestDto updateDto = new UpdateArticleRequestDto(newTitle, newDescription, newBody);
        String requestBody = objectMapper.writeValueAsString(updateDto);

        //when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/api/articles/" + slug)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        //then
        mockMvc.perform(mockRequest).andExpect(status().isCreated())
                .andExpect(jsonPath("$..title").value(newTitle))
                .andExpect(jsonPath("$..slug").value(Slug.of(newTitle, 1L, false).getValue()))
                .andExpect(jsonPath("$..description").value(newDescription))
                .andExpect(jsonPath("$..body").value(newBody));
    }
}