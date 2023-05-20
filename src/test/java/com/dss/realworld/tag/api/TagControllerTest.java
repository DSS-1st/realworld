package com.dss.realworld.tag.api;

import com.dss.realworld.tag.app.TagService;
import com.dss.realworld.tag.domain.Tag;
import com.dss.realworld.tag.domain.repository.TagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(value = "classpath:db/TagTeardown.sql")
@SpringBootTest
@AutoConfigureMockMvc
public class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TagService tagService;

    @Autowired
    private TagRepository tagRepository;

    @DisplayName(value = "저장된 tag가 존재하면 tag 목록 불러오기 성공")
    @Test
    void t1() throws Exception {
        //given
        String name1 = "asdf";
        String name2 = "qwer";
        String name3 = "zxcv";
        tagRepository.persist(new Tag(name1));
        tagRepository.persist(new Tag(name2));
        tagRepository.persist(new Tag(name3));

        //when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/api/tags");
        ResultActions resultActions = mockMvc.perform(mockRequest);
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.tags.size()").value(3))
                .andExpect(jsonPath("$.tags[0]").value(name1))
                .andExpect(jsonPath("$.tags[1]").value(name2))
                .andExpect(jsonPath("$.tags[2]").value(name3));
    }
}