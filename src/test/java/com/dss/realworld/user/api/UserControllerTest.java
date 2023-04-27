package com.dss.realworld.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
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
class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void Should_Success_When_AddUserDtoIsNotNull() throws Exception {
        String username = "Jacob";
        String email = "jake@jake.jake";
        AddUserRequestDto.AddUserDto user = AddUserRequestDto.AddUserDto.builder()
                .username(username)
                .email(email)
                .password("jakejake")
                .build();
        AddUserRequestDto addUserRequestDto = new AddUserRequestDto(user);

        String jsonString = objectMapper.writeValueAsString(addUserRequestDto);
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
}