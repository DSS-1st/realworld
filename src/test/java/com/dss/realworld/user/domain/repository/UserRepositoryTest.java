package com.dss.realworld.user.domain.repository;

import com.dss.realworld.error.exception.UserNotFoundException;
import com.dss.realworld.user.domain.User;
import com.dss.realworld.util.UserFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        clearTable();
    }

    @AfterEach
    void tearDown() {
        clearTable();
    }

    private void clearTable() {
        userRepository.deleteAll();
        userRepository.resetAutoIncrement();
    }

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

        User addedUser = userRepository.findByUsername("Jacob000").orElseThrow(UserNotFoundException::new);
        Assertions.assertThat(addedUser.getUsername()).isEqualTo(newUser1.getUsername());
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

        User addedUser = userRepository.findByEmail("jake000@jake.jake");
        Assertions.assertThat(addedUser.getEmail()).isEqualTo(newUser1.getEmail());
    }
}
