package com.dss.realworld.user.domain.repository;

import com.dss.realworld.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    @Test
    void Should_Success_When_FindByAddedUsername() {
        userRepository.add(newUser1);
        userRepository.add(newUser2);

        GetUserDto addedUser = userRepository.getByUsername("Jacob");
        Assertions.assertThat(addedUser.getUsername()).isEqualTo(newUser1.getUsername());
    }

    @Test
    void Should_Success_When_FindByAddedEmail() {
        userRepository.add(newUser1);
        userRepository.add(newUser2);

        GetUserDto addedUser = userRepository.getByEmail("jake@jake.jake");
        Assertions.assertThat(addedUser.getEmail()).isEqualTo(newUser1.getEmail());
    }
}
