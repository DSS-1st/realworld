package com.dss.realworld.user.app;

import com.dss.realworld.user.api.UpdateUserRequestDto;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.user.domain.repository.UserRepository;
import com.dss.realworld.util.UserFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(value = "classpath:db/UserTeardown.sql")
@SpringBootTest
public class UserSerivceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @DisplayName(value = "기존아이디,updateUserResponseDto 값 유효하면 업데이트 성공")
    @Test
    void t1() {
        User newUser1 = UserFixtures.create();
        userRepository.persist(newUser1);
        User user = userRepository.findByUsername(newUser1.getUsername());

        UpdateUserRequestDto updateUserRequestDto = UpdateUserRequestDto.builder()
                .username("name")
                .email("email")
                .password("1234")
                .bio("bio")
                .build();

        User updatedUser = userService.update(updateUserRequestDto, user.getId());

        assertThat(updatedUser.getEmail()).isEqualTo(updateUserRequestDto.getEmail());
    }
}