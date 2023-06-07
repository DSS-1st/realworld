package com.dss.realworld.common;

import com.dss.realworld.common.jwt.JwtVO;
import com.dss.realworld.user.api.dto.LoginRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = "test")
@Sql(value = {"classpath:db/teardown.sql", "classpath:db/dataSetup.sql"})
@AutoConfigureMockMvc
@SpringBootTest
public class JwtAuthenticationFilterTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName(value = "회원가입된 계정으로 로그인 성공")
    @Test
    void t1() throws Exception {
        //given
        String email = "jake@jake.jake";
        LoginRequestDto loginRequest = LoginRequestDto.builder()
                .email(email)
                .password("jakejake")
                .build();
        String requestBody = objectMapper.writeValueAsString(loginRequest);
        System.out.println("requestBody = " + requestBody);

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print());
        String jwtToken = resultActions.andReturn().getResponse().getHeader(JwtVO.HEADER);

        //then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user.email").value(email));
        assertThat(jwtToken).isNotEmpty();
        assertThat(jwtToken.startsWith(JwtVO.TOKEN_PREFIX)).isTrue();
    }

    @DisplayName(value = "비밀번호가 일치하지 않으면 예외 발생")
    @Test
    void t2() throws Exception {
        //given
        String email = "jake@jake.jake";
        LoginRequestDto loginRequest = LoginRequestDto.builder()
                .email(email)
                .password("jakejake1")
                .build();
        String requestBody = objectMapper.writeValueAsString(loginRequest);
        System.out.println("requestBody = " + requestBody);

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.statusCode").value(401))
                .andExpect(jsonPath("$.errors.body[0]").value("로그인 실패"));
    }
}