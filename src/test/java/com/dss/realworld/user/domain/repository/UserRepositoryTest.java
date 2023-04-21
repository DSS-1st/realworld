package com.dss.realworld.user.domain.repository;

import com.dss.realworld.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void Should_Success_When_FindByAddedUsername() {
        User newUser1 = User.builder()
                .username("Jacob")
                .password("jakejake")
                .email("jake@jake.jake")
                .build();
        User newUser2 = User.builder()
                .username("Kate")
                .password("katekate")
                .email("kate@kate.kate")
                .build();

        userRepository.addUser(newUser1);
        userRepository.addUser(newUser2);

        GetUserDto addedUser = userRepository.getUserByUsername("Jacob");
        Assertions.assertThat(addedUser.getUsername()).isEqualTo(newUser1.getUsername());
    }

    @Test
    void Should_Success_When_FindByAddedEmail() {
        User newUser1 = User.builder()
                .username("Jacob")
                .password("jakejake")
                .email("jake@jake.jake")
                .build();
        User newUser2 = User.builder()
                .username("Kate")
                .password("katekate")
                .email("kate@kate.kate")
                .build();

        userRepository.addUser(newUser1);
        userRepository.addUser(newUser2);

        GetUserDto addedUser = userRepository.getUserByEmail("jake@jake.jake");
        Assertions.assertThat(addedUser.getEmail()).isEqualTo(newUser1.getEmail());
    }
}
