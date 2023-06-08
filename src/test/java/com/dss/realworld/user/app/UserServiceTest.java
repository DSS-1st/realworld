package com.dss.realworld.user.app;

import com.dss.realworld.common.error.exception.UserNotFoundException;
import com.dss.realworld.user.api.dto.LoginRequestDto;
import com.dss.realworld.user.api.dto.UpdateUserRequestDto;
import com.dss.realworld.user.api.dto.UserResponseDto;
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
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @DisplayName(value = "updateUserRequestDto 값이 유효하면 업데이트 성공")
    @Test
    void t1() {
        //given
        Long loginId = 1L;

        UpdateUserRequestDto updateUserRequestDto = UpdateUserRequestDto.builder()
                .username("name")
                .email("email")
                .password("1234")
                .bio("bio")
                .build();

        //when
        UserResponseDto userResponseDto = userService.update(updateUserRequestDto, loginId);

        //then
        assertThat(userResponseDto.getEmail()).isEqualTo(updateUserRequestDto.getEmail());
    }

    @DisplayName(value = "로그인 성공 후 회원정보 반환 성공")
    @Test
    void t2() {
        //given
        String savedUsername = "Jacob";

        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("jake@jake.jake")
                .password("jakejake")
                .build();

        //when
        UserResponseDto userResponseDto = userService.login(loginRequestDto);

        //then
        assertThat(userResponseDto.getUsername()).isEqualTo(savedUsername);
    }

    @DisplayName(value = "로그인된 현재 회원 정보 가져오기")
    @WithUserDetails(value = "jake@jake.jake")
    @Test
    void t3() {
        //given
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User foundUser = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        //when
        UserResponseDto currentUser = userService.get(foundUser.getId());

        //then
        assertThat(currentUser.getUsername()).isEqualTo("Jacob");
        assertThat(currentUser.getEmail()).isEqualTo("jake@jake.jake");
    }
}