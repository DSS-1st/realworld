package com.dss.realworld.article.api;

import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.api.dto.UpdateArticleRequestDto;
import com.dss.realworld.article.domain.Article;
import com.dss.realworld.article.domain.Slug;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.user.domain.repository.FollowingRepository;
import com.dss.realworld.util.ArticleFixtures;
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

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = "test")
@Sql(value = {"classpath:db/teardown.sql", "classpath:db/dataSetup.sql"})
@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private FollowingRepository followingRepository;

    @DisplayName(value = "필수 입력값이 NotNull일 때 Article 생성 성공(tag 제외)")
    @WithUserDetails(value = "jake@jake.jake")
    @Test
    void t1() throws Exception {
        //given
        String title = "How to train your dragon";
        String description = "Ever wonder how?";
        String body = "You have to believe";
        CreateArticleRequestDto newArticle = ArticleFixtures.createRequestDto(title, description, body);
        Long articleId = articleRepository.findMaxId().orElse(0L) + 1;

        String requestBody = objectMapper.writeValueAsString(newArticle);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody);

        //when
        ResultActions resultActions = mockMvc.perform(mockRequest);

        //then
        resultActions
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$..title").value(title))
                .andExpect(jsonPath("$..slug").value(Slug.of(title, articleId).getValue()))
                .andExpect(jsonPath("$..favorited").value(false))
                .andExpect(jsonPath("$..following").value(false))
                .andExpect(jsonPath("$..username").value("Jacob"))
                .andExpect(jsonPath("$..description").value(description))
                .andExpect(jsonPath("$..body").value(body))
                .andExpect(jsonPath("$..tags.size()").value(0));
    }

    @DisplayName(value = "slug, userId가 유효하면 Article 삭제 성공")
    @WithUserDetails(value = "jake@jake.jake")
    @Test
    void t2() throws Exception {
        //given
        Article foundArticle = articleRepository.findById(1L).get();

        //when
        String slug = foundArticle.getSlug();
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .delete("/api/articles/" + slug);

        //then
        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @DisplayName(value = "로그인 상태에서 유효한 slug 사용 시 조회 성공")
    @WithUserDetails(value = "jake@jake.jake")
    @Test
    void t3() throws Exception {
        //given
        String slug = "new-title-2";

        //when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/articles/" + slug);

        //then
        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.article.slug").value(slug))
                .andExpect(jsonPath("$.article.author.following").value(true));
    }


    @DisplayName(value = "로그인 없이도 유효한 slug 사용 시 조회 성공")
    @Test
    void t3_1() throws Exception {
        //given
        String slug = "new-title-1";

        //when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/articles/" + slug);

        //then
        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.article.slug").value(slug))
                .andExpect(jsonPath("$.article.author.following").value(false));
    }

    @DisplayName(value = "Article 수정 시 slug도 수정 성공")
    @WithUserDetails(value = "jake@jake.jake")
    @Test
    void t4() throws Exception {
        //given
        String slug = "new-title-1";
        int slugId = 1;

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
        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$..title").value(newTitle))
                .andExpect(jsonPath("$..slug").value(Slug.of(newTitle, (long) slugId).getValue()))
                .andExpect(jsonPath("$..description").value(newDescription))
                .andExpect(jsonPath("$..body").value(newBody));
    }

    @DisplayName(value = "필수 입력값이 NotNull일 때 Article 생성 성공(tag 포함)")
    @WithUserDetails(value = "jake@jake.jake")
    @Test
    void t5() throws Exception {
        //given
        String title = "How to train your dragon";
        String description = "Ever wonder how?";
        String body = "You have to believe";
        String tag1 = "abc";
        String tag2 = "abc";
        String tag3 = "def";
        String tag4 = "hij";
        List<String> tags = List.of(tag1, tag2, tag3, tag4);
        CreateArticleRequestDto newArticle = ArticleFixtures.createRequestDto(title, description, body, tags);
        Long articleId = articleRepository.findMaxId().orElse(0L) + 1;

        String requestBody = objectMapper.writeValueAsString(newArticle);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody);

        //when
        ResultActions resultActions = mockMvc.perform(mockRequest);

        //then
        resultActions
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$..title").value(title))
                .andExpect(jsonPath("$..slug").value(Slug.of(title, articleId).getValue()))
                .andExpect(jsonPath("$..favorited").value(false))
                .andExpect(jsonPath("$..following").value(false))
                .andExpect(jsonPath("$..username").value("Jacob"))
                .andExpect(jsonPath("$..description").value(description))
                .andExpect(jsonPath("$..body").value(body))
                .andExpect(jsonPath("$..tagList.length()").value(3))
                .andExpect(jsonPath("$..tagList[0]").value(tag2))
                .andExpect(jsonPath("$..tagList[1]").value(tag3))
                .andExpect(jsonPath("$..tagList[2]").value(tag4));
    }

    @DisplayName(value = "이미 존재하는 tag(dvorak)도 저장 성공")
    @WithUserDetails(value = "jake@jake.jake")
    @Test
    void t6() throws Exception {
        //given
        String title = "How to train your dragon";
        String description = "Ever wonder how?";
        String body = "You have to believe";
        String tag1 = "abc";
        String tag2 = "abc";
        String tag3 = "def";
        String tag4 = "dvorak";
        List<String> tags = List.of(tag1, tag2, tag3, tag4);
        CreateArticleRequestDto newArticle = ArticleFixtures.createRequestDto(title, description, body, tags);
        Long articleId = articleRepository.findMaxId().orElse(0L) + 1;

        String requestBody = objectMapper.writeValueAsString(newArticle);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody);

        //when
        ResultActions resultActions = mockMvc.perform(mockRequest);

        //then
        resultActions
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$..title").value(title))
                .andExpect(jsonPath("$..slug").value(Slug.of(title, articleId).getValue()))
                .andExpect(jsonPath("$..favorited").value(false))
                .andExpect(jsonPath("$..following").value(false))
                .andExpect(jsonPath("$..username").value("Jacob"))
                .andExpect(jsonPath("$..description").value(description))
                .andExpect(jsonPath("$..body").value(body))
                .andExpect(jsonPath("$..tagList.length()").value(3))
                .andExpect(jsonPath("$..tagList[0]").value(tag2))
                .andExpect(jsonPath("$..tagList[1]").value(tag3))
                .andExpect(jsonPath("$..tagList[2]").value(tag4));
    }

    @DisplayName(value = "article, favoritedId가 유효하면 좋아요 성공")
    @WithUserDetails(value = "jake@jake.jake")
    @Test
    void t7() throws Exception {
        //given
        String slug = "new-title-1";

        //when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/articles/{slug}/favorite", slug)
                .accept(MediaType.APPLICATION_JSON);
        ResultActions resultActions = mockMvc.perform(mockRequest);

        //then
        resultActions
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$..favorited").value(true))
                .andExpect(jsonPath("$..favoritesCount").value(1));
    }

    @DisplayName(value = "article, loginId가 유효하면 좋아요 취소 성공")
    @WithUserDetails(value = "jake@jake.jake")
    @Test
    void t8() throws Exception {
        //given
        String slug = "new-title-1";

        //when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .delete("/api/articles/{slug}/favorite", slug);
        ResultActions resultActions = mockMvc.perform(mockRequest);

        //then
        resultActions
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$..favorited").value(false))
                .andExpect(jsonPath("$..favoritesCount").value(0));
    }

    @DisplayName(value = "팔로우한 유저가 있으면 팔로우한 유저가 작성한 article 불러오기 성공")
    @WithUserDetails(value = "jake@jake.jake")
    @Test
    void t9() throws Exception {
        //given
        String username = "Kate";

        //when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/articles/feed");

        //then
        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.articles[0].author.username").value(username))
                .andExpect(jsonPath("$.articles[0].author.following").value(true))
                .andExpect(jsonPath("$.articles[1].author.username").value(username))
                .andExpect(jsonPath("$.articles[1].author.following").value(true))
                .andExpect(jsonPath("$.articlesCount").value(2));
    }

    @DisplayName(value = "팔로우한 유저가 없으면 빈 목록 반환 성공")
    @WithUserDetails(value = "jake@jake.jake")
    @Test
    void t10() throws Exception {
        //given
        followingRepository.delete(2L, 1L);

        //when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/articles/feed");

        //then
        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.articles").isEmpty())
                .andExpect(jsonPath("$.articlesCount").value(0));
    }

    @DisplayName(value = "Author를 팔로우하고 Author의 게시글을 좋아한 사용자 이름으로 검색 시 게시글 조회 성공")
    @WithUserDetails(value = "jake@jake.jake")
    @Test
    void t11() throws Exception {
        //given
        String favoritedBy = "Jacob";

        //when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/api/articles")
                .param("favorited", favoritedBy);

        //then
        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.articles[0].favorited").value(true))
                .andExpect(jsonPath("$.articles[0].author.following").value(true))
                .andExpect(jsonPath("$.articlesCount").value(1));
    }

    @DisplayName(value = "로그인 없이 게시글 목록 조회 성공")
    @Test
    void t12() throws Exception {
        //given
        //when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/api/articles");

        //then
        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.articles[0].favorited").value(false))
                .andExpect(jsonPath("$.articles[0].author.following").value(false))
                .andExpect(jsonPath("$.articles[1].favorited").value(false))
                .andExpect(jsonPath("$.articles[1].author.following").value(false))
                .andExpect(jsonPath("$.articles[2].favorited").value(false))
                .andExpect(jsonPath("$.articles[2].author.following").value(false))
                .andExpect(jsonPath("$.articlesCount").value(3));
    }

    @DisplayName(value = "검색조건에 해당하는 글이 없을 경우 빈 목록 반환")
    @WithUserDetails(value = "jake@jake.jake")
    @Test
    void t13() throws Exception {
        //given
        String favoritedBy = "Kite";

        //when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/api/articles")
                .param("favorited", favoritedBy);

        //then
        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.articles").isEmpty())
                .andExpect(jsonPath("$.articlesCount").value(0));
    }

    @DisplayName(value = "로그인 없이도 검색조건에 해당하는 글이 없을 경우 빈 목록 반환")
    @Test
    void t14() throws Exception {
        //given
        String favoritedBy = "Kite";

        //when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/api/articles")
                .param("favorited", favoritedBy);

        //then
        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.articles").isEmpty())
                .andExpect(jsonPath("$.articlesCount").value(0));
    }
}