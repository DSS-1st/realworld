package com.dss.realworld.user.app;

import com.dss.realworld.common.error.exception.UserNotFoundException;
import com.dss.realworld.user.api.dto.ProfileResponseDto;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(value = "test")
@Sql(value = {"classpath:db/teardown.sql", "classpath:db/dataSetup.sql"})
@SpringBootTest
public class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserRepository userRepository;

    @DisplayName(value = "username이 유효하면 GetProfileDto 가져오기 성공")
    @WithUserDetails(value = "jake@jake.jake")
    @Test
    void t1() {
        User foundUser = getLoginUser();

        ProfileResponseDto profileDto = profileService.get("Jacob", foundUser.getId());

        assertThat(profileDto.getUsername()).isEqualTo("Jacob");
    }

    private User getLoginUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User foundUser = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return foundUser;
    }

    @DisplayName(value = "팔로우 대상이 유효하면 팔로우 성공 ")
    @WithUserDetails(value = "kate@realworld.com")
    @Test
    void t2() {
        //given
        String targetName = "Jacob";
        User foundUser = getLoginUser();

        //when
        ProfileResponseDto profileResponseDto = profileService.follow(targetName, foundUser.getId());

        //then
        assertThat(profileResponseDto.getUsername()).isEqualTo(targetName);
        assertThat(profileResponseDto.isFollowing()).isEqualTo(true);
    }

    @DisplayName(value = "username, toUserId 유효하면 팔로우 취소")
    @WithUserDetails(value = "kate@realworld.com")
    @Test
    void t3() {
        //given
        String targetName = "Jacob";
        User foundUser = getLoginUser();

        //when
        profileService.follow(targetName, 2L);
        ProfileResponseDto profileResponseDto = profileService.unFollow(targetName, foundUser.getId());

        //then
        assertThat(profileResponseDto.getUsername()).isEqualTo(targetName);
        assertThat(profileResponseDto.isFollowing()).isEqualTo(false);
    }
}