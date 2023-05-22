package com.dss.realworld.article.api;

import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.api.dto.UpdateArticleRequestDto;
import com.dss.realworld.article.app.ArticleService;
import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.Slug;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.user.domain.repository.UserRepository;
import com.dss.realworld.util.ArticleFixtures;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ActiveProfiles(value = "test")
@Sql(value = {"classpath:db/teardown.sql", "classpath:db/dataSetup.sql"})
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
        String title = "How to train your dragon";
        String description = "Ever wonder how?";
        String body = "You have to believe";
        CreateArticleRequestDto newArticle = ArticleFixtures.createRequestDto(title, description, body);
        Long maxId = articleRepository.findMaxId().get();

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
            .andExpect(jsonPath("$..slug").value(Slug.of(title, maxId).getValue()))
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
        Article foundArticle = articleRepository.findById(1L).get();

        //when
        String slug = foundArticle.getSlug();
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .delete("/api/articles/" + slug);

        //then
        mockMvc.perform(mockRequest).andExpect(status().isOk());
    }

    @DisplayName(value = "유효한 slug 사용 시 조회 성공")
    @Test
    void t3() throws Exception {
        //given
        String slug = "How-to-train-your-dragon-1";
        articleRepository.persist(ArticleFixtures.of(slug, 1L));

        //when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/articles/" + slug);

        //then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..slug").value(slug));
    }

    @DisplayName(value = "Article 수정 시 slug도 수정 성공")
    @Test
    void t4() throws Exception {
        //given
        String slug = "new-title-1";
        Long maxId = articleRepository.findMaxId().get();

        String newTitle = "brand new title";
        String newDescription = "brand new description";
        String newBody = "brand new body";
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
                .andExpect(jsonPath("$..slug").value(Slug.of(newTitle, maxId).getValue()))
                .andExpect(jsonPath("$..description").value(newDescription))
                .andExpect(jsonPath("$..body").value(newBody));
    }
}