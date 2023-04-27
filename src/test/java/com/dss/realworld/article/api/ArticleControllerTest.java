package com.dss.realworld.article.api;

import com.dss.realworld.article.api.dto.CreateArticleRequestDto;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void createDefaultUser() {
        User newUser = User.builder()
                .username("Jacob000")
                .email("jake000@jake.jake")
                .password("jakejake")
                .build();
        userRepository.addUser(newUser);
    }

    @Test
    void Should_Success_When_CreateArticleDtoIsNotNull() throws Exception {
        String title = "How to train your dragon";
        String description = "Ever wonder how?";
        String body = "You have to believe";
        CreateArticleRequestDto.CreateArticleDto article = CreateArticleRequestDto.CreateArticleDto.builder()
                .title(title)
                .description(description)
                .body(body)
                .build();

        CreateArticleRequestDto createArticleRequestDto = new CreateArticleRequestDto(article);
        String jsonString = objectMapper.writeValueAsString(createArticleRequestDto);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonString);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..title").value(title))
                .andExpect(jsonPath("$..slug").value(title.trim().replace(" ", "-")))
                .andExpect(jsonPath("$..favorited").value(false))
                .andExpect(jsonPath("$..following").value(false))
                .andExpect(jsonPath("$..username").value("Jacob000"))
                .andExpect(jsonPath("$..description").value(description))
                .andExpect(jsonPath("$..body").value(body))
                .andExpect(jsonPath("$..tagList.length()").value(0));
    }
}
