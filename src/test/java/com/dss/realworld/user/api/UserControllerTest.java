package com.dss.realworld.user.api;

import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(value = "classpath:db/UserTeardown.sql")
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @DisplayName(value = "AddUserRequestDto가 NotNull이면 User 생성 성공")
    @Test
    void t1() throws Exception {
        String username = "Jacob";
        String email = "jake@jake.jake";
        AddUserRequestDto user = AddUserRequestDto.builder()
                .username(username)
                .email(email)
                .password("jakejake")
                .build();

        String jsonString = objectMapper.writeValueAsString(user);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonString);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..username").value(username))
                .andExpect(jsonPath("$..email").value(email))
                .andExpect(jsonPath("$..token").isNotEmpty());
    }

    @DisplayName(value = "updateUserResponseDto 유효하면 업데이트 성공")
    @Test
    void t2() throws Exception {
        User user = UserFixtures.create();
        userRepository.persist(user);

        UpdateUserRequestDto updateUserRequestDto = UpdateUserRequestDto.builder()
                .username("name")
                .password("pw")
                .email("email@naver.com")
                .image("iamgae")
                .build();

        String jsonDto = objectMapper.writeValueAsString(updateUserRequestDto);
        mockMvc.perform(put("/api/users").contentType(MediaType.APPLICATION_JSON).content(jsonDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..username").value(updateUserRequestDto.getUsername()))
                .andExpect(jsonPath("$..email").value(updateUserRequestDto.getEmail()))
                .andExpect(jsonPath("$..image").value(updateUserRequestDto.getImage()));
    }
}