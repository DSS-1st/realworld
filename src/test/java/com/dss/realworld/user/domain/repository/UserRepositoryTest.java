package com.dss.realworld.user.domain.repository;

import com.dss.realworld.common.error.exception.UserNotFoundException;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.util.UserFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(value = "test")
@Sql(value = "classpath:db/teardown.sql")
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName(value = "User 생성 후 username으로 조회 성공")
    @Test
    void t1() {
        //given
        User newUser1 = User.builder()
                .username("json")
                .password("jsonjson")
                .email("json@json.json")
                .build();
        User newUser2 = User.builder()
                .username("kite")
                .password("kitekite")
                .email("kite@kite.kite")
                .build();

        //when
        userRepository.persist(newUser1);
        userRepository.persist(newUser2);
        User addedUser = userRepository.findByUsername("json").orElseThrow(UserNotFoundException::new);

        //then
        Assertions.assertThat(addedUser.getUsername()).isEqualTo(newUser1.getUsername());
    }

    @DisplayName(value = "User 생성 후 email로 조회 성공")
    @Test
    void t2() {
        //given
        User newUser1 = User.builder()
                .username("json")
                .password("jsonjson")
                .email("json@json.json")
                .build();
        User newUser2 = User.builder()
                .username("kite")
                .password("kitekite")
                .email("kite@kite.kite")
                .build();

        //when
        userRepository.persist(newUser1);
        userRepository.persist(newUser2);
        User addedUser = userRepository.findByEmail("json@json.json").orElseThrow(UserNotFoundException::new);

        //then
        assertThat(addedUser.getEmail()).isEqualTo(newUser1.getEmail());
    }

    @DisplayName(value = "기존 유저 찾아서 업데이트 성공")
    @Test
    void t3() {
        //given
        User user = UserFixtures.create();
        userRepository.persist(user);
        User foundUser = userRepository.findByUsername(user.getUsername()).orElseThrow(UserNotFoundException::new);

        User updateValue = User.builder()
                .email("jj@naver.com")
                .image("image")
                .username("blabla")
                .password("1234")
                .build();

        //when
        userRepository.update(updateValue, foundUser.getId());
        User updatedUser = userRepository.findById(foundUser.getId()).orElseThrow(UserNotFoundException::new);

        //then
        assertThat(updatedUser.getEmail()).isEqualTo(updateValue.getEmail());
    }
}