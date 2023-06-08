package com.dss.realworld.common;

import com.dss.realworld.common.jwt.JwtProcessor;
import com.dss.realworld.common.jwt.JwtVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles(value = "test")
@Sql(value = {"classpath:db/teardown.sql", "classpath:db/dataSetup.sql"})
@AutoConfigureMockMvc
@SpringBootTest
public class JwtAuthorizationFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProcessor jwtProcessor;

    @DisplayName(value = "허용되지 않은 경로로 접근 시 예외 발생")
    @Test
    void t1() throws Exception {
        //given
        String email = "jake@jake.jake";
        String token = jwtProcessor.create(email);

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/articles/wrong_location")
                .header(JwtVO.HEADER, token))
                .andDo(print());

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(jsonPath("$.statusCode").value(500))
                .andExpect(jsonPath("$.errors.body[0]").value("Request method 'POST' not supported"));
    }

    @DisplayName(value = "토큰 없이 접근 시 예외 발생")
    @Test
    void t2() throws Exception {
        //when
        ResultActions resultActions = mockMvc.perform(post("/api/articles/wrong_location"))
                .andDo(print());

        //then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(jsonPath("$.statusCode").value(401))
                .andExpect(jsonPath("$.errors.body[0]").value("로그인이 필요합니다."));
    }
}