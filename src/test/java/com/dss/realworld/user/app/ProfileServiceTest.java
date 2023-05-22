package com.dss.realworld.user.app;

import com.dss.realworld.user.api.dto.ProfileResponseDto;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.FollowRelationRepository;
import com.dss.realworld.user.domain.repository.UserRepository;
import com.dss.realworld.util.UserFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(value = "classpath:db/FollowRelationTeardown.sql")
public class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRelationRepository followRelationRepository;

    @BeforeEach
    void setUp() {
        User newUser = UserFixtures.create();
        userRepository.persist(newUser);
    }

    @DisplayName(value = "username이 유효하면 GetProfileDto 가져오기 성공")
    @Test
    void t1() {
        ProfileResponseDto profileDto = profileService.get("Jacob000",1L);

        assertThat(profileDto.getUsername()).isEqualTo("Jacob000");
    }

    @DisplayName(value = "username, toUserId 유효하면 팔로우 성공 ")
    @Test
    void t2() {
        //given
        String username = "Jacob000";
        User toUser = User.builder()
                .username("son")
                .email("@naver")
                .password("1234")
                .build();
        userRepository.persist(toUser);
        Long toUserId = toUser.getId();

        //when
        ProfileResponseDto profileResponseDto = profileService.follow(username, toUserId);

        //then
        assertThat(profileResponseDto.getUsername()).isEqualTo("Jacob000");
        assertThat(profileResponseDto.isFollowing()).isEqualTo(true);
    }

    @DisplayName("username, toUserId 유효하면 팔로우 취소")
    @Test
    void t3() {
        //given
        String username = "Jacob000";
        User toUser = User.builder()
                .username("son")
                .email("@naver")
                .password("1234")
                .build();
        userRepository.persist(toUser);

        Long toUserId = toUser.getId();

        //when
        profileService.follow(username, toUserId);
        ProfileResponseDto profileResponseDto = profileService.unFollow(username, toUserId);

        //then
        assertThat(profileResponseDto.getUsername()).isEqualTo(username);
        assertThat(profileResponseDto.isFollowing()).isEqualTo(false);
    }
}