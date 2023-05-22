package com.dss.realworld.user.app;

import com.dss.realworld.user.api.dto.ProfileResponseDto;
import com.dss.realworld.user.domain.repository.FollowRelationRepository;
import com.dss.realworld.user.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(value = "test")
@Sql(value = {"classpath:db/teardown.sql", "classpath:db/dataSetup.sql"})
@SpringBootTest
public class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;

    @DisplayName(value = "username이 유효하면 GetProfileDto 가져오기 성공")
    @Test
    void t1() {
        ProfileResponseDto profileDto = profileService.get("Jacob000", 1L);

        assertThat(profileDto.getUsername()).isEqualTo("Jacob000");
    }

    @DisplayName(value = "팔로우 대상이 유효하면 팔로우 성공 ")
    @Test
    void t2() {
        //given
        String followTargetName = "kate";

        //when
        ProfileResponseDto profileResponseDto = profileService.follow(followTargetName, 1L);

        //then
        assertThat(profileResponseDto.getUsername()).isEqualTo(followTargetName);
        assertThat(profileResponseDto.isFollowing()).isEqualTo(true);
    }

    @DisplayName("username, toUserId 유효하면 팔로우 취소")
    @Test
    void t3() {
        //given
        String targetName = "kate";

        //when
        profileService.follow(targetName, 1L);
        ProfileResponseDto profileResponseDto = profileService.unFollow(targetName, 1L);

        //then
        assertThat(profileResponseDto.getUsername()).isEqualTo(targetName);
        assertThat(profileResponseDto.isFollowing()).isEqualTo(false);
    }
}