package com.dss.realworld.user.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = "test")
@Sql(value = {"classpath:db/teardown.sql", "classpath:db/dataSetup.sql"})
@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName(value = "username이 유효하면 조회 성공")
    @Test
    void t1() throws Exception {
        //given
        String username = "kate";

        //when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/profiles/" + username);

        //then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..username").value(username));
    }

    @DisplayName(value = "username이 유효하면 팔로우 성공")
    @Test
    void t2() throws Exception {
        //given
        String targetName = "Jacob";

        mockMvc.perform(post("/api/profiles/{username}/follow", targetName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..username").value(targetName))
                .andExpect(jsonPath("$..following").value(true));
    }

    @DisplayName(value = "username이 유효하면 팔로우 취소")
    @Test
    void t3() throws Exception {
        String targetName = "kate";

        mockMvc.perform(delete("/api/profiles/{username}/follow", targetName))
                .andExpect(status().isOk());
    }
}