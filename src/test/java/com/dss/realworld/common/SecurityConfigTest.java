package com.dss.realworld.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles(value = "test")
@AutoConfigureMockMvc
@SpringBootTest
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName(value = "사용자 인증 없이 인증이 필요한 주소로 접근 시 에러 메시지 반환")
    @Test
    void t1() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(post("/api/articles"))
                .andDo(print())
                .andExpect(jsonPath("$.statusCode").value(401))
                .andExpect(jsonPath("$.statusReason").value("Unauthorized"))
                .andExpect(jsonPath("$.errors.body[0]").value("로그인이 필요합니다."));
    }
}