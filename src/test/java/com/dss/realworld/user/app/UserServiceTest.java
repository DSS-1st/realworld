package com.dss.realworld.user.app;

import com.dss.realworld.user.api.dto.LoginUserRequestDto;
import com.dss.realworld.user.api.dto.UpdateUserRequestDto;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(value = {"classpath:db/teardown.sql", "classpath:db/dataSetup.sql"})
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @DisplayName(value = "updateUserRequestDto 값이 유효하면 업데이트 성공")
    @Test
    void t1() {
        //given
        Long loginUserId = 1L;

        UpdateUserRequestDto updateUserRequestDto = UpdateUserRequestDto.builder()
                .username("name")
                .email("email")
                .password("1234")
                .bio("bio")
                .build();

        //when
        User updatedUser = userService.update(updateUserRequestDto, loginUserId);

        //then
        assertThat(updatedUser.getEmail()).isEqualTo(updateUserRequestDto.getEmail());
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

        User loginUser = userService.login(loginUserRequestDto);

        assertThat(loginUser.getUsername()).isEqualTo(savedUsername);
    }
}