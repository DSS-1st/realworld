package com.dss.realworld.user.api;

import com.dss.realworld.user.app.ProfileService;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.FollowRelationRepository;
import com.dss.realworld.user.domain.repository.UserRepository;
import com.dss.realworld.util.UserFixtures;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class ProfileControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private FollowRelationRepository followRelationRepository;

    @BeforeEach
    void setUp() {
        clearTable();

        User newUser = UserFixtures.create();
        userRepository.persist(newUser);
    }

    @AfterEach
    void teatDown() {
        clearTable();
    }

    private void clearTable() {
        userRepository.deleteAll();
        userRepository.resetAutoIncrement();

        followRelationRepository.deleteAll();
        followRelationRepository.resetAutoIncrement();
    }

    @DisplayName(value = "username 유효하면 조회 성공")
    @Test
    void t1() throws Exception {
        //given
        String username = "Jacob000";

        //when
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/profiles/" + username);

        //then
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..username").value(username));
    }

    @DisplayName(value = "username과 toUserId 유효하면 팔로우 성공")
    @Test
    void t2() throws Exception {
        String username = "Jacob000";

        User toUser = User.builder()
                .username("test1")
                .email("@google.com")
                .password("1234")
                .build();
        userRepository.persist(toUser);

        Long toUserId = toUser.getId();

        mockMvc.perform(post("/api/profiles/{username}/follow", username)
                        .param("toUserId", String.valueOf(toUserId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..username").value("Jacob000"))
                .andExpect(jsonPath("$..following").value(true));
    }

    @DisplayName(value = "username과 followerId가 유효하면 팔로우 취소")
    @Test
    void t3() throws Exception {
        String username = "Jacob000";

        User toUser = User.builder()
                .username("test1")
                .email("@google.com")
                .password("1234")
                .build();
        userRepository.persist(toUser);

        Long toUserId = toUser.getId();

        mockMvc.perform(delete("/api/profiles/{username}/follow", username)
                        .param("toUserId", String.valueOf(toUserId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}