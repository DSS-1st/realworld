package com.dss.realworld.article.api;

import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.article.api.dto.CreateArticleRequestDto.CreateArticleDto;
import com.dss.realworld.article.app.ArticleService;
import com.dss.realworld.article.domain.dto.GetArticleDto;
import com.dss.realworld.article.domain.repository.ArticleRepository;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @BeforeEach
    void setUp() {
        clearTable();

        User newUser = User.builder()
                .username("Jacob000")
                .email("jake000@jake.jake")
                .password("jakejake")
                .build();
        userRepository.addUser(newUser);
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
    }

    @DisplayName(value = "slug, userId가 유효하면 Article 삭제 성공")
    @Test
    void t1() throws Exception {
        //given
        Long logonId = 1L;
        CreateArticleRequestDto articleDto = createArticleDto();
        GetArticleDto savedArticle = articleService.createArticle(articleDto, logonId);
        assertThat(savedArticle.getUserId()).isEqualTo(logonId);

        //when
        String slug = savedArticle.getSlug();
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .delete("/api/articles/" + slug)
                .contentType(MediaType.TEXT_PLAIN);

        //then
        mockMvc.perform(mockRequest).andExpect(status().isOk());
    }

    private CreateArticleRequestDto createArticleDto() {
        CreateArticleDto createArticleDto = CreateArticleDto.builder()
                .title("How to train your dragon")
                .description("Ever wonder how?")
                .body("You have to believe")
                .build();

        return new CreateArticleRequestDto(createArticleDto);
    }

    @DisplayName(value = "필수 입력값이 NotNull일 때 Article 생성 성공")
    @Test
    void t2() throws Exception {
        //given
        String title = "How to train your dragon";
        String description = "Ever wonder how?";
        String body = "You have to believe";
        CreateArticleDto article = CreateArticleDto.builder()
                .title(title)
                .description(description)
                .body(body)
                .build();

        //when
        CreateArticleRequestDto createArticleRequestDto = new CreateArticleRequestDto(article);
        String jsonString = objectMapper.writeValueAsString(createArticleRequestDto);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonString);

        //then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..title").value(title))
                .andExpect(jsonPath("$..slug").value(title.trim().replace(" ", "-") + "-1"))
                .andExpect(jsonPath("$..favorited").value(false))
                .andExpect(jsonPath("$..following").value(false))
                .andExpect(jsonPath("$..username").value("Jacob000"))
                .andExpect(jsonPath("$..description").value(description))
                .andExpect(jsonPath("$..body").value(body))
                .andExpect(jsonPath("$..tagList.length()").value(0));
    }
}
