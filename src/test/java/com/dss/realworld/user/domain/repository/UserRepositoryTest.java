package com.dss.realworld.user.domain.repository;

import com.dss.realworld.error.exception.UserNotFoundException;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.util.UserFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(value = "classpath:db/UserTeardown.sql")
@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName(value = "User 생성 후 username으로 조회 성공")
    @Test
    void t1() {
        User newUser1 = UserFixtures.create();
        User newUser2 = User.builder()
                .username("Kate")
                .password("katekate")
                .email("kate@kate.kate")
                .build();

        userRepository.persist(newUser1);
        userRepository.persist(newUser2);

        User addedUser = userRepository.findByUsername("Jacob000");
        assertThat(addedUser.getUsername()).isEqualTo(newUser1.getUsername());
    }

    @DisplayName(value = "User 생성 후 email로 조회 성공")
    @Test
    void t2() {
        User newUser1 = UserFixtures.create();
        User newUser2 = User.builder()
                .username("Kate")
                .password("katekate")
                .email("kate@kate.kate")
                .build();

        userRepository.persist(newUser1);
        userRepository.persist(newUser2);

        User addedUser = userRepository.findByEmail("jake000@jake.jake").orElseThrow(UserNotFoundException::new);
        assertThat(addedUser.getEmail()).isEqualTo(newUser1.getEmail());
    }

    @DisplayName(value = "기존유저 찾아서 업데이트하기")
    @Test
    void t3() {
        //given
        User newUser1 = UserFixtures.create();
        userRepository.persist(newUser1);
        User user = userRepository.findByUsername(newUser1.getUsername());

        User updateValue = User.builder()
                .email("jj@naver.com")
                .image("image")
                .username("blabla")
                .password("1234")
                .build();

        //when
        userRepository.update(updateValue,user.getId());

        //then
        User updatedUser = userRepository.findById(user.getId());
        assertThat(updatedUser.getEmail()).isEqualTo(updateValue.getEmail());
    }
}