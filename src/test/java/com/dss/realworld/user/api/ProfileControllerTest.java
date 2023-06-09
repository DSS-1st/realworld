package com.dss.realworld.user.api;

import com.dss.realworld.user.domain.repository.FollowingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value = "test")
@Sql(value = {"classpath:db/teardown.sql", "classpath:db/dataSetup.sql"})
@AutoConfigureMockMvc
@SpringBootTest
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FollowingRepository followingRepository;

    @DisplayName(value = "로그인 상태에서 username이 유효하면 조회 성공")
    @WithUserDetails(value = "jake@jake.jake")
    @Test
    void t1() throws Exception {
        //given
        String username = "Kate";

        //when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/profiles/" + username);

        //then
        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.profile.username").value(username))
                .andExpect(jsonPath("$.profile.following").value(true));
    }

    @DisplayName(value = "로그인 없이도 username이 유효하면 조회 성공")
    @Test
    void t1_1() throws Exception {
        //given
        String username = "Kate";

        //when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/profiles/" + username);

        //then
        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.profile.username").value(username))
                .andExpect(jsonPath("$.profile.following").value(false));
    }

    @DisplayName(value = "username이 유효하면 팔로우 성공")
    @WithUserDetails(value = "jake@jake.jake")
    @Test
    void t2() throws Exception {
        //given
        followingRepository.delete(2L, 1L);
        String targetName = "Kate";

        //when
        //then
        mockMvc.perform(post("/api/profiles/{username}/follow", targetName))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.profile.username").value(targetName))
                .andExpect(jsonPath("$.profile.following").value(true));
    }

    @DisplayName(value = "username이 유효하면 팔로우 취소")
    @WithUserDetails(value = "jake@jake.jake")
    @Test
    void t3() throws Exception {
        //given
        String targetName = "Kate";

        //when
        //then
        mockMvc.perform(delete("/api/profiles/{username}/follow", targetName))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.profile.username").value(targetName))
                .andExpect(jsonPath("$.profile.following").value(false));
    }

    @DisplayName(value = "이미 팔로우한 상태이면 예외 메시지 반환")
    @WithUserDetails(value = "jake@jake.jake")
    @Test
    void t4() throws Exception {
        //given
        String targetName = "Kate";

        //when
        //then
        mockMvc.perform(post("/api/profiles/{username}/follow", targetName))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors.body[0]").value("이미 팔로우한 사용자입니다."));
    }
}