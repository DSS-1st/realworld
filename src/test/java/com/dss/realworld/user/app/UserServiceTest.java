package com.dss.realworld.user.app;

import com.dss.realworld.user.api.dto.LoginUserRequestDto;
import com.dss.realworld.user.api.dto.UpdateUserRequestDto;
import com.dss.realworld.user.api.dto.UserResponseDto;
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
public class UserServiceTest {

    @Autowired
    private UserService userService;

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

    @DisplayName(value = "로그인 정보가 일치하면 로그인된 회원정보 반환 성공")
    @Test
    void t2() {
        //given
        String savedUsername = "Jacob000";

        LoginUserRequestDto loginUserRequestDto = LoginUserRequestDto.builder()
                .email("jake000@jake.jake")
                .password("jakejake")
                .build();

        UserResponseDto userResponseDto = userService.login(loginUserRequestDto);

        assertThat(userResponseDto.getUsername()).isEqualTo(savedUsername);
    }

    @DisplayName(value = "현재 회원 정보 가져오기")
    @Test
    void t3() {
        Long loginUserId = 1L;

        UserResponseDto currentUser = userService.get(loginUserId);

        assertThat(currentUser.getUsername()).isEqualTo("Jacob000");
        assertThat(currentUser.getEmail()).isEqualTo("jake000@jake.jake");
    }
}