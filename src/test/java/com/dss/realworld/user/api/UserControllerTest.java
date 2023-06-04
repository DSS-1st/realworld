package com.dss.realworld.user.api;

import com.dss.realworld.user.api.dto.AddUserRequestDto;
import com.dss.realworld.user.api.dto.LoginUserRequestDto;
import com.dss.realworld.user.api.dto.UpdateUserRequestDto;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = "test")
@Sql(value = {"classpath:db/teardown.sql", "classpath:db/dataSetup.sql"})
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName(value = "AddUserRequestDto가 NotNull이면 User 생성 성공")
    @Test
    void t1() throws Exception {
        //given
        String username = "Jacob100";
        String email = "jake100@jake.jake";
        AddUserRequestDto user = AddUserRequestDto.builder()
                .username(username)
                .email(email)
                .password("jakejake")
                .build();

        //when
        String jsonString = objectMapper.writeValueAsString(user);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonString);

        //then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..username").value(username))
                .andExpect(jsonPath("$..email").value(email))
                .andExpect(jsonPath("$..token").isNotEmpty());
    }

    @DisplayName(value = "updateUserResponseDto가 유효하면 업데이트 성공")
    @Test
    void t2() throws Exception {
        //given
        UpdateUserRequestDto updateRequestDto = UpdateUserRequestDto.builder()
                .username("name")
                .password("pw")
                .email("email@naver.com")
                .image("iamgae")
                .build();
        //when
        String requestBody = objectMapper.writeValueAsString(updateRequestDto);

        //then
        mockMvc.perform(put("/api/user")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..username").value(updateRequestDto.getUsername()))
                .andExpect(jsonPath("$..email").value(updateRequestDto.getEmail()))
                .andExpect(jsonPath("$..image").value(updateRequestDto.getImage()));
    }

    @DisplayName(value = "loginUserRequestDto가 유효하면 로그인 회원정보 반환 성공")
    @Test
    void t3() throws Exception {
        //given
        String loginUsername = "Jacob";
        String loginUserEmail = "jake@jake.jake";
        LoginUserRequestDto loginUserRequestDto = LoginUserRequestDto.builder()
                .email(loginUserEmail)
                .password("jakejake")
                .build();

        //when
        String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(loginUserRequestDto);

        //then
        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..username").value(loginUsername))
                .andExpect(jsonPath("$..email").value(loginUserEmail));
    }

    //todo 스프링 시큐리티 적용 후 수정
    @DisplayName(value = "현재 로그인한 계정 정보 반환 성공")
    @Test
    void t4() throws Exception {
        //given
        String username = "Jacob";
        String email = "jake@jake.jake";

        //when
        //then
        mockMvc.perform(get("/api/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..username").value(username))
                .andExpect(jsonPath("$..email").value(email));
    }

    @DisplayName(value = "username이 중복이면 예외 메시지 반환")
    @Test
    void t5() throws Exception {
        //given
        String username = "Jacob";
        String email = "jake@jake.jake";
        AddUserRequestDto user = AddUserRequestDto.builder()
                .username(username)
                .email(email)
                .password("jakejake")
                .build();

        //when
        String jsonString = objectMapper.writeValueAsString(user);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonString);

        //then
        mockMvc.perform(mockRequest)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors.body[0]").value("이미 존재하는 사용자 이름입니다."));
    }

    @DisplayName(value = "email이 중복이면 예외 메시지 반환")
    @Test
    void t6() throws Exception {
        //given
        String username = "Jacob123";
        String email = "jake@jake.jake";
        AddUserRequestDto user = AddUserRequestDto.builder()
                .username(username)
                .email(email)
                .password("jakejake")
                .build();

        //when
        String jsonString = objectMapper.writeValueAsString(user);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonString);

        //then
        mockMvc.perform(mockRequest)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors.body[0]").value("이미 존재하는 Email입니다."));
    }
}