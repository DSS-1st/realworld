package com.dss.realworld.user.app;

import com.dss.realworld.user.api.ProfileResponseDto;
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
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
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
        clearTable();

        User newUser = UserFixtures.create();
        userRepository.persist(newUser);
    }

    private void clearTable() {
        userRepository.deleteAll();
        userRepository.resetAutoIncrement();
    }
    @DisplayName("username이 유효하면 GetProfileDto 가져오기 성공")
    @Test
    void t1() {
        ProfileResponseDto profileDto = profileService.get("Jacob000");

        assertThat(profileDto.getUsername()).isEqualTo("Jacob000");
    }

    @DisplayName("followee 유저 네임, followerId 유효하면 팔로우 성공 ")
    @Test
    void t2() {
        String followeeUsername = "Jacob000";
        User user2 = User.builder()
                .username("son")
                .email("@naver")
                .password("1234")
                .build();
        userRepository.persist(user2);
        Long followerId = user2.getId();

        ProfileResponseDto profileResponseDto = profileService.follow(followeeUsername, followerId);

        assertThat(profileResponseDto.getUsername()).isEqualTo("Jacob000");
        assertThat(profileResponseDto.isFollowing()).isEqualTo(true);
    }
}